package com.mydrinksapp.data

import androidx.lifecycle.LiveData
import com.mydrinksapp.core.Resource
import com.mydrinksapp.data.local.CocktailEntity
import com.mydrinksapp.data.local.LocalDataSource
import com.mydrinksapp.data.remote.RemoteDataSource
import com.mydrinksapp.domain.CocktailRepoInterface
import com.mydrinksapp.data.model.AllIngredientList
import com.mydrinksapp.data.model.CategoriesList
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailByCategoryList
import com.mydrinksapp.data.model.CocktailList
import com.mydrinksapp.data.model.IngredientList
import com.mydrinksapp.data.model.asCocktailEntity
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CocktailRepoInterface {

    /*------------------------------ Search ------------------------------*/
    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>> =
        callbackFlow {

            trySend(getCachedCocktails(cocktailName))

            remoteDataSource.getCocktailByName(cocktailName).collect {
                when (it) {
                    is Resource.Success -> {
                        for (cocktail in it.data) {
                            saveCocktail(cocktail.asCocktailEntity())
                        }
                        trySend(getCachedCocktails(cocktailName))
                    }
                    is Resource.Failure -> {
                        trySend(getCachedCocktails(cocktailName))
                    }
                    else -> {}
                }
            }
            awaitClose { cancel() }
        }

    /*------------------------------ Favorites ------------------------------*/
    override suspend fun saveFavoriteCocktail(cocktail: Cocktail) {
        localDataSource.saveFavoriteCocktail(cocktail)
    }

    override suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean =
        localDataSource.isCocktailFavorite(cocktail)

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        localDataSource.saveCocktail(cocktail)
    }

    override fun getFavoritesCocktails(): LiveData<List<Cocktail>> {
        return localDataSource.getFavoritesCocktails()
    }

    override suspend fun deleteFavoriteCocktail(cocktail: Cocktail) {
        localDataSource.deleteCocktail(cocktail)
    }

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>> {
        return localDataSource.getCachedCocktails(cocktailName)
    }

    /*------------------------------ Random ------------------------------*/
    override suspend fun getRandomCocktails(): CocktailList {
        return remoteDataSource.getRandomCocktails()
    }

    /*------------------------------ Cocktails By Categories ------------------------------*/
    override suspend fun getCocktailsByCategories(categoryName: String): CocktailByCategoryList {
        return remoteDataSource.getCocktailsByCategories(categoryName)
    }

    /*------------------------------ Cocktails By Glass ------------------------------*/
    override suspend fun getCocktailsByGlass(glassName: String): CocktailByCategoryList {
        return remoteDataSource.getCocktailsByGlass(glassName)
    }

    /*------------------------------ Detail cocktails by Id ------------------------------*/
    override suspend fun getCocktailById(idDrink: String): CocktailList {
        return remoteDataSource.getCocktailById(idDrink)
    }

    /*------------------------------ Detail cocktails by Letter ------------------------------*/
    override suspend fun getCocktailByLetter(letter: String): CocktailList {
        return remoteDataSource.getCocktailByLetter(letter)
    }

    /*------------------------------ Cocktails by Ingredients ------------------------------*/
    override suspend fun getCocktailByIngredients(ingredients: String): CocktailList {
        return remoteDataSource.getCocktailByIngredients(ingredients)
    }

    /*------------------------------ Ingredients by Name ------------------------------*/
    override suspend fun getIngredientsByName(ingredients: String): IngredientList {
        return remoteDataSource.getIngredientsByName(ingredients)
    }

    /*------------------------------ All ingredients list ------------------------------*/
    override suspend fun getAllIngredientsList(ingredient: String): AllIngredientList {
        return remoteDataSource.getAllIngredientsList(ingredient)
    }

    /*------------------------------ All categories list ------------------------------*/
    override suspend fun getAllCategoriesList(categoryName: String): CategoriesList {
        return remoteDataSource.getAllCategoriesList(categoryName)
    }
}