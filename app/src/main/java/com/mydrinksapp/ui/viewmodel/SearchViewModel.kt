package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.base.Resource
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    private val mutableCocktailName = MutableLiveData<String>()

    fun setCocktail(cocktailName: String) {
        mutableCocktailName.value = cocktailName
    }

    init {
        setCocktail("margarita")
    }

    val fetchCocktailList = mutableCocktailName.distinctUntilChanged().switchMap { cocktailName ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                repo.getCocktailByName(cocktailName).collect {
                    emit(it)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}