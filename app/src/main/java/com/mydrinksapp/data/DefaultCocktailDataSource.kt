package com.mydrinksapp.data

import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.data.model.asCocktailEntity
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DefaultCocktailDataSource @Inject constructor(
    private val networkDataSource: DataSource,
    private val localDataSource: DataSource
) : DataSource {

    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?> =
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

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
        localDataSource.saveFavoriteCocktail(cocktail)
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        localDataSource.saveCocktail(cocktail)
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
        return localDataSource.getFavoritesCocktails()
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
        localDataSource.deleteCocktail(cocktail)
    }

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? {
        return localDataSource.getCachedCocktails(cocktailName)
    }
}