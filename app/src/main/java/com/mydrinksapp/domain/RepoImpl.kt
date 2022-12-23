package com.mydrinksapp.domain

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val datasource: DataSource) : Repo {

    override suspend fun getTragosList(tragoName: String): Resource<List<Drink>> {
        return datasource.getTragoByName(tragoName)!!
    }

    override suspend fun getTragosFavoritos(): Resource<List<Drink>> {
        return datasource.getTragosFavoritos()
    }

    override suspend fun insertTrago(trago: DrinkEntity) {
        datasource.insertTragoIntoRoom(trago)
    }

    override suspend fun deleteDrink(drink: DrinkEntity): Resource<List<Drink>> {
        datasource.deleteDrink(drink)
        return getTragosFavoritos()
    }
}