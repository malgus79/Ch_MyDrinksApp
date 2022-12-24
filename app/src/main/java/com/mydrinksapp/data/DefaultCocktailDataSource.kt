package com.mydrinksapp.data

import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.data.model.asCocktailEntity
import com.mydrinksapp.vo.Resource
import javax.inject.Inject

class DefaultCocktailDataSource @Inject constructor(
    private val networkDataSource: DataSource,
    private val localDataSource: DataSource
) : DataSource {

    override suspend fun getCocktailByName(cocktailName: String): Resource<List<Cocktail>>? {

        when (val cocktailList = networkDataSource.getCocktailByName(cocktailName)) {
            is Resource.Success -> {
                for (cocktail in cocktailList.data) {
                    saveCocktail(cocktail.asCocktailEntity())
                }
            }
            is Resource.Failure -> {
                return getCocktails(cocktailName)
            }
            else -> {}
        }
        return getCocktails(cocktailName)
    }

    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
        localDataSource.saveFavoriteCocktail(cocktail)
    }

    override suspend fun saveCocktail(cocktail: CocktailEntity) {
        localDataSource.saveCocktail(cocktail)
    }

    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
        return localDataSource.getFavoritesCocktails()
    }

    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
        localDataSource.deleteCocktail(cocktail)
    }

    override suspend fun getCocktails(cocktailName: String): Resource<List<Cocktail>>? {
        return localDataSource.getCocktails(cocktailName)
    }
}