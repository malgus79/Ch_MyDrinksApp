package com.mydrinksapp.data.remote

import com.mydrinksapp.data.CocktailDataSource
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.CocktailEntity
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
//
//class RemoteCocktailDataSourceImpl @Inject constructor(
//    private val webService: WebService
//) : CocktailDataSource {
//
//    override suspend fun getCocktailByName(cocktailName: String): Flow<Resource<List<Cocktail>>> = callbackFlow {
//        trySend(Resource.Success(webService.getCocktailByName(cocktailName)?.cocktailList?: listOf()))
//        awaitClose { close() }
//    }
//
//    override suspend fun getCachedCocktails(cocktailName: String): Resource<List<Cocktail>>? {
//        TODO("not implemented")
//    }
//
//    override suspend fun saveCocktail(cocktail: CocktailEntity) {
//        TODO("not implemented")
//    }
//
//    override suspend fun saveFavoriteCocktail(cocktail: FavoritesEntity) {
//        TODO("not implemented")
//    }
//
//    override suspend fun getFavoritesCocktails(): Resource<List<Cocktail>> {
//        TODO("not implemented")
//    }
//
//    override suspend fun deleteCocktail(cocktail: FavoritesEntity) {
//        TODO("not implemented")
//    }
//}