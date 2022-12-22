package com.mydrinksapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    val imagen: String = "",
    val nombre: String = "",
    val descripcion: String = ""
) : Parcelable