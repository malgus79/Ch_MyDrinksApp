package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource

interface Repo {
    suspend fun getTragosList(tragoName: String): Resource<List<Drink>>
    suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>>
    suspend fun insertTrago(trago:DrinkEntity)
    suspend fun deleteDrink(drink: DrinkEntity)
}