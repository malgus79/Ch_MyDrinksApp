package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.base.Resource
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AllCocktailsViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    private val mutableCocktailsByLetter = MutableLiveData<String>()

    init {
        mutableCocktailsByLetter.value = "A"
    }

    fun setCocktailByLetter(letter: String) {
        mutableCocktailsByLetter.value = letter
    }

    val fetchCocktailByLetter = mutableCocktailsByLetter.distinctUntilChanged().switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(Resource.Success(repo.getCocktailByLetter(it)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}