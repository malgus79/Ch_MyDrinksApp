package com.mydrinksapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mydrinksapp.core.Resource
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.asCocktailList
import com.mydrinksapp.data.model.asDrinkList
import com.mydrinksapp.data.model.asFavoriteEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: CocktailDao) {
    suspend fun saveFavoriteCocktail(cocktail: Cocktail) {
        return dao.saveFavoriteCocktail(cocktail.asFavoriteEntity())
    }

    fun getFavoritesCocktails(): LiveData<List<Cocktail>> {
        return dao.getAllFavoriteDrinksWithChanges().map { it.asDrinkList() }
    }

    suspend fun deleteCocktail(cocktail: Cocktail) {
        return dao.deleteFavoriteCoktail(cocktail.asFavoriteEntity())
    }

    suspend fun saveCocktail(cocktail: CocktailEntity) {
        dao.saveCocktail(cocktail)
    }

    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>> {
        return Resource.Success(dao.getCocktails(cocktailName).asCocktailList())
    }

    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean {
        return dao.getCocktailById(cocktail.cocktailId.toString()) != null
    }
}