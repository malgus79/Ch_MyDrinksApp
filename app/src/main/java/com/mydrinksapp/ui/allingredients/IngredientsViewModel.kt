package com.mydrinksapp.ui.allingredients

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
class IngredientsViewModel @Inject constructor(private val repo: CocktailRepoInterface) :
    ViewModel() {

//    /*------------------------------ Cocktails by Ingredients ------------------------------*/
//    private val mutableCocktailsByIngredients = MutableLiveData<String>()
//
//    init {
//        mutableCocktailsByIngredients.value = "7-Up"
//    }
//
//    fun setCocktailByIngredients(ingredients: String) {
//        mutableCocktailsByIngredients.value = ingredients
//    }
//
//    val fetchCocktailByIngredients =
//        mutableCocktailsByIngredients.distinctUntilChanged().switchMap {
//            liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
//                emit(Resource.Loading)
//                try {
//                    emit(Resource.Success(repo.getCocktailByIngredients(it)))
//                } catch (e: Exception) {
//                    emit(Resource.Failure(e))
//                }
//            }
//        }

//    /*------------------------------ Ingredients by Name ------------------------------*/
//    private val mutableIngredientsByName = MutableLiveData<String>()
//
//    init {
//        mutableIngredientsByName.value = "7-Up"
//    }
//
//    fun setIngredientsByName(ingredients: String) {
//        mutableIngredientsByName.value = ingredients
//    }
//
//    val fetchIngredientsByName =
//        mutableCocktailsByIngredients.distinctUntilChanged().switchMap {
//            liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
//                emit(Resource.Loading)
//                try {
//                    emit(Resource.Success(repo.getIngredientsByName(it)))
//                } catch (e: Exception) {
//                    emit(Resource.Failure(e))
//                }
//            }
//        }

    /*------------------------------ All ingredients list ------------------------------*/
    fun fetchAllIngredientList() =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(Resource.Success(repo.getAllIngredientsList("list")))
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }

    private val mutableAllIngredients = MutableLiveData<String>()

    init {
        mutableAllIngredients.value = "7-Up"
    }

    fun setAllIngredients(ingredients: String) {
        mutableAllIngredients.value = ingredients
    }

    fun fetchAllIngredients() =
        mutableAllIngredients.distinctUntilChanged().switchMap {
            liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(Resource.Loading)
                try {
                    emit(
                        Resource.Success(
                            Pair(
                                repo.getCocktailByIngredients(it),
                                repo.getIngredientsByName(it),
                            )
                        )
                    )
                } catch (e: Exception) {
                    Resource.Failure(e)
                }
            }
        }
}