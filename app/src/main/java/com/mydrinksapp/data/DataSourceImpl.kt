package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.domain.DataSource
import com.mydrinksapp.domain.TragosDao
import com.mydrinksapp.vo.Resource
import com.mydrinksapp.vo.RetrofitClient
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val tragosDao: TragosDao) : DataSource {

    override suspend fun getTragoByName(tragoName: String): Resource<List<Drink>> {
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }

    override suspend fun insertTragoIntoRoom(trago: DrinkEntity) {
        tragosDao.insertFavorite(trago)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> {
        return Resource.Success(tragosDao.getAllFavoriteDrinks())
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        tragosDao.deleteDrink(drink)
    }
}

/*
suspend fun getTragoByName(tragoName: String): Resource<List<Drink>> {
    val resultRequest = RetrofitClient.webservice.getTragoByName(tragoName).drinkList
    resultRequest?.let {
        return Resource.Success(it)
    }
    val e = Exception("Result Not Found!!")
    return Resource.Failure(e)
}
*/
