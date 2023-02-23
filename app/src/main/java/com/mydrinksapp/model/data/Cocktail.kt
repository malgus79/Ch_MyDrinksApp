package com.mydrinksapp.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cocktail(
    @SerializedName("idDrink") val cocktailId: String? = "",
    @SerializedName("strDrinkThumb") val image: String? = "",
    @SerializedName("strDrink") val name: String? = "",
    @SerializedName("strInstructions") val description: String? = "",
    @SerializedName("strAlcoholic") val hasAlcohol: String? = "Non_Alcoholic"
) : Parcelable

data class CocktailList(
    @SerializedName("drinks") val drinks: List<Cocktail> = listOf()
)