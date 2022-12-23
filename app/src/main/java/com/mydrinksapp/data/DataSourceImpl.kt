package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.data.model.asDrinkList
import com.mydrinksapp.domain.service.TragosDao
import com.mydrinksapp.domain.service.WebService
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val tragosDao: TragosDao,
    private val webService: WebService
) : DataSource {

    override suspend fun getTragoByName(nombreTrago: String): Resource<List<Drink>> {
        return Resource.Success(webService.getTragoByName(nombreTrago)?.drinkList?: listOf())
    }

    override suspend fun insertTragoIntoRoom(trago: DrinkEntity) {
        tragosDao.insertFavorite(trago)
    }

    override suspend fun getTragosFavoritos(): Resource<List<Drink>> {
        return Resource.Success(tragosDao.getAllFavoriteDrinks().asDrinkList())
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
