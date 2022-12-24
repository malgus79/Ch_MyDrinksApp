package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.domain.Repo
import com.mydrinksapp.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repo: Repo) : ViewModel() {

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
                emit(repo.getCocktailList(cocktailName))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveCocktail(cocktail: DrinkEntity) {
        viewModelScope.launch {
            repo.insertCocktail(cocktail)
        }
    }

    fun getFavoriteCocktails() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try{
            emit(repo.getFavoriteCocktails())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteCocktail(cocktail: DrinkEntity) = liveData(viewModelScope.coroutineContext + Dispatchers.IO)  {
        emit(Resource.Loading())
        try{
            emit(repo.deleteCocktail(cocktail))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}