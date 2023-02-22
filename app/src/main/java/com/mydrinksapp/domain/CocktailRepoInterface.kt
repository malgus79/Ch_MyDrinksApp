package com.mydrinksapp.domain

import androidx.lifecycle.LiveData
import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.model.data.CocktailByCategoryList
import com.mydrinksapp.model.data.CocktailList
import com.mydrinksapp.model.local.CocktailEntity
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
}