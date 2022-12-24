package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.data.model.asDrinkList
import com.mydrinksapp.domain.service.CocktailDao
import com.mydrinksapp.domain.service.WebService
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val webService: WebService
) : DataSource {

    override suspend fun getCocktailByName(nombreTrago: String): Resource<List<Drink>> {
        return Resource.Success(webService.getCocktailByName(nombreTrago)?.drinkList?: listOf())
    }

    override suspend fun insertCocktailIntoRoom(trago: DrinkEntity) {
        cocktailDao.insertFavorite(trago)
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Drink>> {
        return Resource.Success(cocktailDao.getAllFavoriteDrinks().asDrinkList())
    }

    override suspend fun deleteCocktail(drink: DrinkEntity) {
        cocktailDao.deleteCoktail(drink)
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
