package com.mydrinksapp.ui.allcocktails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mydrinksapp.core.Resource
import com.mydrinksapp.domain.CocktailRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AllCocktailsViewModel @Inject constructor(private val repo: CocktailRepoInterface) :
    ViewModel() {

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