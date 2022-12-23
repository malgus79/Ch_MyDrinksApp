package com.mydrinksapp.domain

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.vo.Resource

class RepoImpl(private val datasource: DataSource) : Repo {

    override suspend fun getTragosList(tragoName: String): Resource<List<Drink>> {
        return datasource.getTragoByName(tragoName)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> {
        return datasource.getTragosFavoritos()
    }

    override suspend fun insertTrago(trago: DrinkEntity) {
        datasource.insertTragoIntoRoom(trago)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        datasource.deleteDrink(drink)
    }
}