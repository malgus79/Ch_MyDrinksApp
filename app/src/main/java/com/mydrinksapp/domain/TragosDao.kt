package com.mydrinksapp.domain

import androidx.room.*
import com.mydrinksapp.data.model.DrinkEntity

@Dao
interface TragosDao {

    @Query("SELECT * FROM tragosEntity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trago: DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)
}