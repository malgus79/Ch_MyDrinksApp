package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getCocktailList(cocktailName:String): Flow<Resource<List<Cocktail>>?>
    suspend fun getFavoriteCocktails(): Resource<List<Cocktail>>
    suspend fun saveCocktail(cocktail:FavoritesEntity)
    suspend fun deleteCocktail(cocktail: FavoritesEntity):Resource<List<Cocktail>>
}