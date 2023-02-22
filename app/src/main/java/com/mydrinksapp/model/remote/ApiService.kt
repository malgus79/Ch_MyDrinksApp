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
    suspend fun getCocktailsByCategories(@Query(value = "c") categoryName: String): CocktailByCategoryList

    @GET("filter.php")
    suspend fun getCocktailsByGlass(@Query(value = "g") glassName: String): CocktailByCategoryList

    @GET("lookup.php")
    suspend fun getCocktailById(@Query(value = "i") idDrink: String): CocktailList


    @GET("search.php")
    suspend fun getCocktailByLetter(@Query(value = "f") letter: String): CocktailList
}