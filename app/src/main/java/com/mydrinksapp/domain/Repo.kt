package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.vo.Resource

interface Repo {
    suspend fun getTragosList(tragoName: String): Resource<List<Drink>>
}