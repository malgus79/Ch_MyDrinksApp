package com.mydrinksapp.model.remote

import com.mydrinksapp.model.data.CocktailByCategoryList
import com.mydrinksapp.model.data.CocktailList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun getCocktailByName(@Query(value = "s") cocktailName: String): CocktailList?

    @GET("random.php")
    suspend fun getRandomCocktails(): CocktailList

    @GET("filter.php")
    suspend fun getPopularCocktails(@Query(value = "c") categoryName : String): CocktailByCategoryList
}