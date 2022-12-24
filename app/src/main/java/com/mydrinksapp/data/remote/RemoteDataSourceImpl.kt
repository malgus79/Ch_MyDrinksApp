package com.mydrinksapp.data.remote

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.domain.remote.WebService
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val webService: WebService
) : DataSource {

    override suspend fun getCocktailByName(cocktailName: String): Resource<List<Cocktail>> {
        return Resource.Success(
            webService.getCocktailByName(cocktailName)?.cocktailList ?: listOf()
        )
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        TODO("not implemented")
    }

    override suspend fun getCocktails(cocktailName: String): Resource<List<Cocktail>>? {
        TODO("not implemented")
    }

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
        TODO("not implemented")
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
        TODO("not implemented")
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
        TODO("not implemented")
    }
}