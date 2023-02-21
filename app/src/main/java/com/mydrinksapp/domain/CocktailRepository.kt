package com.mydrinksapp.domain

import androidx.lifecycle.LiveData
import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.data.CocktailList
import com.mydrinksapp.model.data.asCocktailEntity
import com.mydrinksapp.model.local.CocktailEntity
import com.mydrinksapp.model.local.LocalDataSource
import com.mydrinksapp.model.remote.RemoteDataSource
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
}