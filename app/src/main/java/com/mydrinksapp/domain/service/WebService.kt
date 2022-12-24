package com.mydrinksapp.domain.service

import com.mydrinksapp.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("search.php")
    suspend fun getCocktailByName(@Query(value = "s") tragoName:String): DrinkList?
}