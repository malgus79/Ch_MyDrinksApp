package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mydrinksapp.base.Resource
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    fun getFavoriteCocktails() =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emitSource(repo.getFavoritesCocktails().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun deleteFavoriteCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            repo.deleteFavoriteCocktail(cocktail)
//            toastHelper.sendToast("Cocktail deleted from favorites")
        }
    }
}