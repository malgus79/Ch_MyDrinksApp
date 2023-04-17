package com.mydrinksapp.domain

import androidx.lifecycle.LiveData
import com.mydrinksapp.core.Resource
import com.mydrinksapp.data.local.CocktailEntity
import com.mydrinksapp.data.model.AllIngredientList
import com.mydrinksapp.data.model.CategoriesList
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailByCategoryList
import com.mydrinksapp.data.model.CocktailList
import com.mydrinksapp.data.model.IngredientList
import kotlinx.coroutines.flow.Flow

interface CocktailRepoInterface {
    /*------------------------------ Search ------------------------------*/
    suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>>

    /*------------------------------ Favorites ------------------------------*/
    suspend fun saveFavoriteCocktail(cocktail: Cocktail)
    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean
    suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>
    suspend fun saveCocktail(cocktail: CocktailEntity)
    fun getFavoritesCocktails(): LiveData<List<Cocktail>>
    suspend fun deleteFavoriteCocktail(cocktail: Cocktail)

    /*------------------------------ Random ------------------------------*/
    suspend fun getRandomCocktails(): CocktailList

    /*------------------------------ Cocktails By Categories ------------------------------*/
    suspend fun getCocktailsByCategories(categoryName: String): CocktailByCategoryList

    /*------------------------------ Cocktails By Glass ------------------------------*/
    suspend fun getCocktailsByGlass(glassName: String): CocktailByCategoryList

    /*------------------------------ Detail cocktails by Id ------------------------------*/
    suspend fun getCocktailById(idDrink: String): CocktailList

    /*------------------------------ Detail cocktails by Letter ------------------------------*/
    suspend fun getCocktailByLetter(letter: String): CocktailList

    /*------------------------------ Cocktails by Ingredients ------------------------------*/
    suspend fun getCocktailByIngredients(ingredients: String): CocktailList

    /*------------------------------ Ingredients by name ------------------------------*/
    suspend fun getIngredientsByName(ingredients: String): IngredientList

    /*------------------------------ All ingredients list ------------------------------*/
    suspend fun getAllIngredientsList(ingredient: String): AllIngredientList

    /*------------------------------ All categories list ------------------------------*/
    suspend fun getAllCategoriesList(categoryName: String): CategoriesList

}