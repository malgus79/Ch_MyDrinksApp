package com.mydrinksapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mydrinksapp.data.model.*
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val cocktailDao: CocktailDao) {
    suspend fun saveFavoriteCocktail(cocktail: Cocktail) {
        return cocktailDao.saveFavoriteCocktail(cocktail.asFavoriteEntity())
    }

    fun getFavoritesCocktails(): LiveData<List<Cocktail>> {
        return cocktailDao.getAllFavoriteDrinksWithChanges().map { it.asDrinkList() }
    }

    suspend fun deleteCocktail(cocktail: Cocktail) {
        return cocktailDao.deleteFavoriteCoktail(cocktail.asFavoriteEntity())
    }

    suspend fun saveCocktail(cocktail: CocktailEntity) {
        cocktailDao.saveCocktail(cocktail)
    }

    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>> {
        return Resource.Success(cocktailDao.getCocktails(cocktailName).asCocktailList())
    }

    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean {
        return cocktailDao.getCocktailById(cocktail.cocktailId) != null
    }
}