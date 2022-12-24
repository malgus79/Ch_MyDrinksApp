package com.mydrinksapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.domain.service.CocktailDao

@Database(entities = arrayOf(DrinkEntity::class), version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
}