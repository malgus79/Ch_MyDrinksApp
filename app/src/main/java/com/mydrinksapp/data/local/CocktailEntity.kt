package com.mydrinksapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail_entity")
data class CocktailEntity(
    @PrimaryKey val cocktailId: String,
    @ColumnInfo(name = "cocktail_image") val image: String = "",
    @ColumnInfo(name = "cocktail_name") val name: String = "",
    @ColumnInfo(name = "cocktail_description") val description: String = "",
    @ColumnInfo(name = "cocktail_has_alcohol") val hasAlcohol: String = "Non_Alcoholic"
)

@Entity(tableName = "favorites_entity")
data class FavoritesEntity(
    @PrimaryKey val cocktailId: String,
    @ColumnInfo(name = "cocktail_image") val image: String = "",
    @ColumnInfo(name = "cocktail_name") val name: String = "",
    @ColumnInfo(name = "cocktail_description") val description: String = "",
    @ColumnInfo(name = "cocktail_has_alcohol") val hasAlcohol: String = "Non_Alcoholic"
)
