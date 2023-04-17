package com.mydrinksapp.data.model

import com.google.gson.annotations.SerializedName

data class AllIngredient(
    @SerializedName("strIngredient1") val ingredientName: String? = ""
)

data class AllIngredientList(
    val drinks: List<AllIngredient> = listOf()
)