package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource

interface DataSource {
    suspend fun getCocktailByName(nombreTrago: String): Resource<List<Drink>>?
    suspend fun insertCocktailIntoRoom(trago: DrinkEntity)
    suspend fun getFavoritesCocktails(): Resource<List<Drink>>
    suspend fun deleteCocktail(drink: DrinkEntity)
}