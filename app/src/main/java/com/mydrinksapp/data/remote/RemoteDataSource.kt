package com.mydrinksapp.data.remote

import com.mydrinksapp.core.Resource
import com.mydrinksapp.data.model.AllIngredientList
import com.mydrinksapp.data.model.CategoriesList
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailByCategoryList
import com.mydrinksapp.data.model.CocktailList
import com.mydrinksapp.data.model.IngredientList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService) {
    suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>> =
        callbackFlow {
            trySend(
                Resource.Success(
                    api.getCocktailByName(cocktailName)?.drinks ?: listOf()
                )
            )
            awaitClose { close() }
        }

    suspend fun getRandomCocktails(): CocktailList {
        return api.getRandomCocktails()
    }

    suspend fun getCocktailsByCategories(categoryName: String): CocktailByCategoryList {
        return api.getCocktailsByCategories(categoryName)
    }

    suspend fun getCocktailsByGlass(glassName: String): CocktailByCategoryList {
        return api.getCocktailsByGlass(glassName)
    }

    suspend fun getCocktailById(idDrink: String): CocktailList {
        return api.getCocktailById(idDrink)
    }

    suspend fun getCocktailByLetter(letter: String): CocktailList {
        return api.getCocktailByLetter(letter)
    }

    suspend fun getCocktailByIngredients(ingredients: String): CocktailList {
        return api.getCocktailByIngredients(ingredients)
    }

    suspend fun getIngredientsByName(ingredients: String): IngredientList {
        return api.getIngredientsByName(ingredients)
    }

    suspend fun getAllIngredientsList(ingredient: String): AllIngredientList {
        return api.getAllIngredientsList(ingredient)
    }

    suspend fun getAllCategoriesList(categoryName: String): CategoriesList {
        return api.getAllCategoriesList(categoryName)
    }
}