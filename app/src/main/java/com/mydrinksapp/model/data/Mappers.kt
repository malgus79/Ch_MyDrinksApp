package com.mydrinksapp.model.data

import com.mydrinksapp.model.local.CocktailEntity
import com.mydrinksapp.model.local.FavoritesEntity

fun List<FavoritesEntity>.asDrinkList(): List<Cocktail> = this.map {
    Cocktail(it.cocktailId, it.image, it.name, it.description, it.hasAlcohol)
}

fun List<CocktailEntity>.asCocktailList(): List<Cocktail> = this.map {
    Cocktail(it.cocktailId, it.image, it.name, it.description, it.hasAlcohol)
}

fun Cocktail.asCocktailEntity(): CocktailEntity =
    CocktailEntity(
        this.cocktailId.toString(),
        this.image.toString(),
        this.name.toString(),
        this.description.toString(),
        this.hasAlcohol.toString()
    )

fun Cocktail.asFavoriteEntity(): FavoritesEntity =
    FavoritesEntity(
        this.cocktailId.toString(),
        this.image.toString(),
        this.name.toString(),
        this.description.toString(),
        this.hasAlcohol.toString()
    )