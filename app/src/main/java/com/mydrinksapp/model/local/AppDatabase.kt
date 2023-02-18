package com.mydrinksapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritesEntity::class, CocktailEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
}