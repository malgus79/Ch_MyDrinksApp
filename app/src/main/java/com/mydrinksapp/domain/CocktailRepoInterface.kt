package com.mydrinksapp.domain

import androidx.lifecycle.LiveData
import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.local.CocktailEntity
import com.mydrinksapp.model.data.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepoInterface {
    suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>>
    suspend fun saveFavoriteCocktail(cocktail: Cocktail)
    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean
    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>
    suspend fun saveCocktail(cocktail: CocktailEntity)
    fun getFavoritesCocktails(): LiveData<List<Cocktail>>
    suspend fun deleteFavoriteCocktail(cocktail: Cocktail)
}