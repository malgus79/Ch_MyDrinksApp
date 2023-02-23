package com.mydrinksapp.model.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.data.asCocktailList
import com.mydrinksapp.model.data.asDrinkList
import com.mydrinksapp.model.data.asFavoriteEntity
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