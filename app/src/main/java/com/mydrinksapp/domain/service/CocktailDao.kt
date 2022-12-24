package com.mydrinksapp.domain.service

import androidx.room.*
import com.mydrinksapp.data.model.DrinkEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM tragosEntity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trago: DrinkEntity)

    @Delete
    suspend fun deleteCoktail(drink: DrinkEntity)
}