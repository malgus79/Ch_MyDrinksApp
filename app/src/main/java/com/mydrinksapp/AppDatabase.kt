package com.mydrinksapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.domain.TragosDao

@Database(entities = arrayOf(DrinkEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tragoDao(): TragosDao
}