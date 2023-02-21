package com.mydrinksapp.model.data

import com.google.gson.annotations.SerializedName

data class CocktailByCategory(
    @SerializedName("idDrink") val cocktailId: String = "",
    @SerializedName("idDrink") val name: String = "",
    @SerializedName("idDrink") val image: String = ""
)

data class CocktailByCategoryList(
    @SerializedName("bebidas") val bebidas: List<CocktailByCategory> = listOf()
)