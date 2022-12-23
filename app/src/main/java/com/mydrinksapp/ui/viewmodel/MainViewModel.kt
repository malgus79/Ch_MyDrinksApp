package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.domain.Repo
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {

    private val tragosData = MutableLiveData<String>()

    fun setTrago(tragoName: String) {
        tragosData.value = tragoName
    }

    init {
        setTrago("margarita")
    }


    val fectchTragosList = tragosData.distinctUntilChanged().switchMap { nombreTrago ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getTragosList(nombreTrago))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun guardarTrago(trago:DrinkEntity) {
        viewModelScope.launch {
            repo.insertTrago(trago)
        }
    }

    fun getTragosFavoritos() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getTragosFavoritos())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrink(drink: DrinkEntity) {
        viewModelScope.launch {
            repo.deleteDrink(drink)
        }
    }
}