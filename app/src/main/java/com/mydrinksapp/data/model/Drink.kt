package com.mydrinksapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    @SerializedName("strDrinkThumb") val imagen: String = "",
    @SerializedName("strDrink") val nombre: String = "",
    @SerializedName("strInstructions") val descripcion: String = "",
    @SerializedName("strAlcoholic") val hasAlcohol: String = "Non_Alcoholic"
) : Parcelable

data class DrinkList(
    @SerializedName("drinks") val drinkList: List<Drink>
)