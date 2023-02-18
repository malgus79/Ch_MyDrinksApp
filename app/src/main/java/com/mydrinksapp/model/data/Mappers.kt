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
    CocktailEntity(this.cocktailId, this.image, this.name, this.description, this.hasAlcohol)

fun Cocktail.asFavoriteEntity(): FavoritesEntity =
    FavoritesEntity(this.cocktailId, this.image, this.name, this.description, this.hasAlcohol)