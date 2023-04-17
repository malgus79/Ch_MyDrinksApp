package com.mydrinksapp.data.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("strCategory") val category: String? = ""
)

data class CategoriesList(
    @SerializedName("drinks") val drinks: List<Categories> = listOf()
)