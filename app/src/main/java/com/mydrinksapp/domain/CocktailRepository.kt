package com.mydrinksapp.domain

import androidx.lifecycle.LiveData
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>>
    suspend fun saveFavoriteCocktail(cocktail: Cocktail)
    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean
    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>
    suspend fun saveCocktail(cocktail: CocktailEntity)
    fun getFavoritesCocktails(): LiveData<List<Cocktail>>
    suspend fun deleteFavoriteCocktail(cocktail: Cocktail)
}