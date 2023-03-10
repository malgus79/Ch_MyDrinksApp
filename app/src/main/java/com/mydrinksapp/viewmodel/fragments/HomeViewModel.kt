package com.mydrinksapp.viewmodel.fragments

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

    fun fetchAllCocktailsInHome(categoryName: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(
                    Resource.Success(
                        Triple(
                            repo.getRandomCocktails(),
                            repo.getCocktailsByCategories(categoryName),
                            repo.getAllCategoriesList("list")
                        )
                    )
                )
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
}