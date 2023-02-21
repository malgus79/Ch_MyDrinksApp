package com.mydrinksapp.model.data

import com.google.gson.annotations.SerializedName

data class CocktailByCategory(
    @SerializedName("idDrink") val cocktailId: String = "",
    @SerializedName("strDrink") val name: String = "",
    @SerializedName("strDrinkThumb") val image: String = ""
)

data class CocktailByCategoryList(
    @SerializedName("drinks") val drinks: List<CocktailByCategory> = listOf()
)