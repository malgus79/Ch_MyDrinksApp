package com.mydrinksapp.viewmodel.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            } else {
                repo.saveFavoriteCocktail(cocktail)
            }
        }
    }

    suspend fun isCocktailFavorite(cocktail: Cocktail): Boolean =
        repo.isCocktailFavorite(cocktail)


    /*------------------------------ Detail cocktails by Id ------------------------------*/
    private var _cocktailByLetterDetailMutableLiveData = MutableLiveData<Cocktail>()
    val cocktailByLetterDetailMutableLiveData: LiveData<Cocktail> =
        _cocktailByLetterDetailMutableLiveData

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
}