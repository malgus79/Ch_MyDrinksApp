package com.mydrinksapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    @SerializedName("idDrink")
    val cocktailId: String = "",
    @SerializedName("strDrinkThumb")
    val image: String = "",
    @SerializedName("strDrink")
    val name: String = "",
    @SerializedName("strInstructions")
    val description: String = "",
    @SerializedName("strAlcoholic")
    val hasAlcohol: String = "Non_Alcoholic"
) : Parcelable

data class DrinkList(
    @SerializedName("drinks") val drinkList: List<Drink> = listOf()
)

@Entity(tableName = "tragosEntity")
data class DrinkEntity(
    @PrimaryKey
    val cocktailId: String,
    @ColumnInfo(name = "trago_imagen")
    val image: String = "",
    @ColumnInfo(name = "trago_nombre")
    val name: String = "",
    @ColumnInfo(name = "trago_descripcion")
    val description: String = "",
    @ColumnInfo(name = "trago_has_alcohol")
    val hasAlcohol: String = "Non_Alcoholic"
)

fun List<DrinkEntity>.asDrinkList(): MutableList<Drink> =
    this.map {
        Drink(
            it.cocktailId,
            it.image,
            it.name,
            it.description,
            it.hasAlcohol
        )
    }.toMutableList()

fun Drink.asDrinkEntity(): DrinkEntity =
    DrinkEntity(
        this.cocktailId,
        this.image,
        this.name,
        this.description,
        this.hasAlcohol
    )