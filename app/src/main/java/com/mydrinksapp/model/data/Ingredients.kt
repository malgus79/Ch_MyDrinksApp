package com.mydrinksapp.model.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient") val idIngredient: String? = "",
    @SerializedName("strABV") val abv: String? = "",
    @SerializedName("strAlcohol") val alcohol: String? = "",
    @SerializedName("strDescription") val description: String? = "",
    @SerializedName("strIngredient") val name: String? = "",
    @SerializedName("strType") val type: String? = ""
)

data class IngredientList(
    @SerializedName("ingredients") val ingredients: List<Ingredient> = listOf()
)
