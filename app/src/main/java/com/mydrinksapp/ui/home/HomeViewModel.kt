package com.mydrinksapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mydrinksapp.core.Resource
import com.mydrinksapp.domain.CocktailRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: CocktailRepoInterface) : ViewModel() {

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