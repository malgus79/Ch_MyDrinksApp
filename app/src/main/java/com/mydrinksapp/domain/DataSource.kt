package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource

interface DataSource {
    suspend fun getTragoByName(tragoName: String): Resource<List<Drink>>
    suspend fun insertTragoIntoRoom(trago: DrinkEntity)
    suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>>
    suspend fun deleteDrink(drink: DrinkEntity)
}