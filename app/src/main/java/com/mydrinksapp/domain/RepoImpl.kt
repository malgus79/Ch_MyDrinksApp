package com.mydrinksapp.domain

import com.mydrinksapp.data.DefaultCocktailDataSource
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val defaultDataSource: DefaultCocktailDataSource
) : Repo {

    override suspend fun getCocktailList(cocktailName: String): Resource<List<Cocktail>> {
        return defaultDataSource.getCocktailByName(cocktailName)!!
    }

    override suspend fun getFavoriteCocktails(): Resource<List<Cocktail>> {
        return defaultDataSource.getFavoritesCocktails()
    }

    override suspend fun saveCocktail(cocktail: FavoritesEntity) {
        defaultDataSource.saveFavoriteCocktail(cocktail)
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity): Resource<List<Cocktail>> {
        defaultDataSource.deleteCocktail(cocktail)
        return getFavoriteCocktails()
    }

    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>> {
        return defaultDataSource.getCocktails(cocktailName)!!
    }
}