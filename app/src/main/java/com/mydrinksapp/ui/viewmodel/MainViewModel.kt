package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mydrinksapp.domain.Repo
import com.mydrinksapp.vo.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repo: Repo) : ViewModel() {

    val fectchTragosList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getTragosList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}