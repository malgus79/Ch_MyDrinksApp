package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.domain.CocktailRepository
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val dataSource: CocktailRepository) : ViewModel() {

    private val mutableCocktailName = MutableLiveData<String>()

    fun setCocktail(cocktailName: String) {
        mutableCocktailName.value = cocktailName
    }

    init {
        setCocktail("margarita")
    }

    val fetchCocktailList = mutableCocktailName.distinctUntilChanged().switchMap { cocktailName ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                dataSource.getCocktailByName(cocktailName).collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveOrDeleteFavoriteCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            if (dataSource.isCocktailFavorite(cocktail)) {
                dataSource.deleteFavoriteCocktail(cocktail)
//                Toast.makeText(this@MainViewModel, "", Toast.LENGTH_SHORT).show()
//                toastHelper.sendToast("Cocktail deleted from favorites")
            } else {
                dataSource.saveFavoriteCocktail(cocktail)
//                toastHelper.sendToast("Cocktail saved to favorites")
            }
        }
    }

    fun getFavoriteCocktails() =
        liveData<Resource<List<Cocktail>>>(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emitSource(dataSource.getFavoritesCocktails().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun deleteFavoriteCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            dataSource.deleteFavoriteCocktail(cocktail)
//            toastHelper.sendToast("Cocktail deleted from favorites")
        }
    }

    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean =
        dataSource.isCocktailFavorite(cocktail)
}