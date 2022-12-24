package com.mydrinksapp.data

import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.vo.Resource

interface DataSource {
    suspend fun getCocktailByName(cocktailName: String): Resource<List<Cocktail>>?
    suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity)
    suspend fun saveCocktail(cocktail: CocktailEntity)
    suspend fun getCocktails(cocktailName: String): Resource<List<Cocktail>>?
    suspend fun getFavoritesCocktails(): Resource<List<Cocktail>>
    suspend fun deleteCocktail(cocktail: FavoritesEntity)
}