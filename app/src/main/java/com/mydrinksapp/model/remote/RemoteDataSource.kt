package com.mydrinksapp.model.remote

import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.data.CocktailByCategoryList
import com.mydrinksapp.model.data.CocktailList
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

    suspend fun getPopularCocktails(categoryName: String): CocktailByCategoryList {
        return api.getPopularCocktails(categoryName)
    }
}