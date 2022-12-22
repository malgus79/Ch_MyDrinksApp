package com.mydrinksapp.domain

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.vo.Resource

interface Repo {
    fun getTragosList(): Resource<List<Drink>>
}