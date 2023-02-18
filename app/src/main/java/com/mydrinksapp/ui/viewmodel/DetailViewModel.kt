package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
}