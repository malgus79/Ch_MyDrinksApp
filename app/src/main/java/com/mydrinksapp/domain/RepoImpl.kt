package com.mydrinksapp.domain

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val dataSource: DataSource) : Repo {

    override suspend fun getCocktailList(cocktailName:String): Resource<List<Drink>> {
        return dataSource.getCocktailByName(cocktailName)!!
    }

    override suspend fun getFavoriteCocktails(): Resource<List<Drink>> {
        return dataSource.getFavoritesCocktails()
    }

    override suspend fun insertCocktail(cocktail: DrinkEntity) {
        dataSource.insertCocktailIntoRoom(cocktail)
    }

    override suspend fun deleteCocktail(cocktail: DrinkEntity): Resource<List<Drink>> {
        dataSource.deleteCocktail(cocktail)
        return getFavoriteCocktails()
    }
}