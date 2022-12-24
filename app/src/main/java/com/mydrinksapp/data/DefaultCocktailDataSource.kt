package com.mydrinksapp.data

import androidx.lifecycle.LiveData
import com.mydrinksapp.data.local.LocalDataSource
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.asCocktailEntity
import com.mydrinksapp.data.remote.NetworkDataSource
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DefaultCocktailDataSource @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : CocktailDataSource {

    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>> =
        callbackFlow {

            trySend(getCachedCocktails(cocktailName))

            networkDataSource.getCocktailByName(cocktailName).collect {
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
}