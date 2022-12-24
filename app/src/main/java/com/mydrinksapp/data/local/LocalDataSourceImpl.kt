package com.mydrinksapp.data.local

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.*
import com.mydrinksapp.domain.local.CocktailDao
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val cocktailDao: CocktailDao) : DataSource {

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
        return cocktailDao.saveFavoriteCocktail(cocktail)
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
        return Resource.Success(cocktailDao.getAllFavoriteDrinks().asDrinkList())
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
        return cocktailDao.deleteFavoriteCoktail(cocktail)
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        cocktailDao.saveCocktail(cocktail)
    }

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? {
        return Resource.Success(cocktailDao.getCocktails(cocktailName).asCocktailList())
    }

    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>?> {
        TODO("not implemented")
    }
}