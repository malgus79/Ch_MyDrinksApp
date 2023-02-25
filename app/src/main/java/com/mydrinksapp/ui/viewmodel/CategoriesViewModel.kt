package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.*
import com.mydrinksapp.base.Resource
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    private val mutableCocktailsByCategories = MutableLiveData<String>()

    fun setCocktailsByCategories(nameOfCategory: String) {
        mutableCocktailsByCategories.value = nameOfCategory
    }

    val fetchCocktailsByCategories = mutableCocktailsByCategories.distinctUntilChanged().switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(Resource.Success(repo.getCocktailsByCategories(it)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}