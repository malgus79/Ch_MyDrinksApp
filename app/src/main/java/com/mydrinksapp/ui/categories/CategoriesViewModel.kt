package com.mydrinksapp.ui.categories

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
class CategoriesViewModel @Inject constructor(private val repo: CocktailRepoInterface) :
    ViewModel() {

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