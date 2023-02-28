package com.mydrinksapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mydrinksapp.base.ConnectivityNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(connectivityNetwork: ConnectivityNetwork) :
    ViewModel() {

    val isConnected: Flow<Boolean> = connectivityNetwork.isConnected

}