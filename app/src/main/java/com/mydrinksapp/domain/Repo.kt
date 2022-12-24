package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource

interface Repo {
    suspend fun getCocktailList(cocktailName:String): Resource<List<Drink>>?
    suspend fun getFavoriteCocktails(): Resource<List<Drink>>
    suspend fun insertCocktail(cocktail:DrinkEntity)
    suspend fun deleteCocktail(cocktail: DrinkEntity):Resource<List<Drink>>
}