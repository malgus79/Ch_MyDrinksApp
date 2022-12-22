package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.vo.Resource
import com.mydrinksapp.vo.RetrofitClient

class DataSource {

    suspend fun getTragoByName(tragoName: String): Resource<List<Drink>> {
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }
}