package com.mydrinksapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CocktailDao {

    @Query("SELECT * FROM favorites_entity")
    suspend fun getAllFavoriteDrinks(): List<FavoritesEntity>

    @Query("SELECT * FROM favorites_entity")
    fun getAllFavoriteDrinksWithChanges(): LiveData<List<FavoritesEntity>>

    @Query("SELECT * FROM cocktail_entity WHERE cocktail_name LIKE '%' || :cocktailName || '%'") // This Like operator is needed due that the API returns blank spaces in the name
    suspend fun getCocktails(cocktailName: String): List<CocktailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteCocktail(trago: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCocktail(cocktail: CocktailEntity)

    @Delete
    suspend fun deleteFavoriteCoktail(favorites: FavoritesEntity)

    @Query("SELECT * FROM favorites_entity WHERE cocktailId = :cocktailId")
    suspend fun getCocktailById(cocktailId: String): FavoritesEntity?
}