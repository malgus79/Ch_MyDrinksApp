package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mydrinksapp.base.Resource
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    fun fetchRandomCocktails() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repo.getRandomCocktails()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchPopularCocktails(categoryName: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading)
            try {
                emit(Resource.Success(repo.getPopularCocktails(categoryName)))
            } catch (e:Exception) {
                emit(Resource.Failure(e))
            }
        }
}