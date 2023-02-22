package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mydrinksapp.domain.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCocktailsViewModel @Inject constructor(private val repo: CocktailRepository) : ViewModel() {

}