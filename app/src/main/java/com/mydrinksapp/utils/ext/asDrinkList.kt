package com.mydrinksapp.utils.ext

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity

fun List<DrinkEntity>.asDrinkList(): MutableList<Drink> = this.map {
    Drink(it.tragoId, it.imagen, it.nombre, it.descripcion, it.hasAlcohol)
}.toMutableList()