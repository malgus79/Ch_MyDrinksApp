package com.mydrinksapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.mydrinksapp.base.Resource
import com.mydrinksapp.domain.CocktailRepository
import com.mydrinksapp.model.data.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

    fun saveOrDeleteFavoriteCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            if (repo.isCocktailFavorite(cocktail)) {
                repo.deleteFavoriteCocktail(cocktail)
//                Toast.makeText(this@MainViewModel, "", Toast.LENGTH_SHORT).show()
//                toastHelper.sendToast("Cocktail deleted from favorites")
            } else {
                repo.saveFavoriteCocktail(cocktail)
//                toastHelper.sendToast("Cocktail saved to favorites")
            }
        }
    }

    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean =
        repo.isCocktailFavorite(cocktail)



    private var _cocktailByLetterDetailMutableLiveData = MutableLiveData<Cocktail>()
    val cocktailByLetterDetailMutableLiveData: LiveData<Cocktail> = _cocktailByLetterDetailMutableLiveData
    fun fetchCocktailDetailsById(idDrink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getCocktailById(idDrink)

                if (result.drinks.isNotEmpty()) {
                    _cocktailByLetterDetailMutableLiveData.postValue(result.drinks[0])
                } else {
                    Log.d(TAG, "fetchCocktailDetailsById: notSuccessful")
                }
            } catch (e: Exception) {
                Log.d(TAG, "fetchCocktailDetailsById: ${e.message}")
            }
        }
    }


    private val mutableCocktailsById = MutableLiveData<String>()
    fun setCocktailById(letter: String) {
        mutableCocktailsById.value = letter
    }

    val fetchCocktailById = mutableCocktailsById.distinctUntilChanged().switchMap {
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