package com.mydrinksapp.model.remote

import com.mydrinksapp.model.data.AllIngredientList
import com.mydrinksapp.model.data.CocktailByCategoryList
import com.mydrinksapp.model.data.CocktailList
import com.mydrinksapp.model.data.IngredientList
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

    @GET("filter.php")
    suspend fun getCocktailByIngredients(@Query(value = "i") ingredients: String): CocktailList

    @GET("search.php")
    suspend fun getIngredientsByName(@Query(value = "i") ingredients: String): IngredientList

    @GET("list.php")
    suspend fun getAllIngredientsList(@Query(value = "i") ingredient: String): AllIngredientList
}