package com.purewowstudio.socketiochat.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewowstudio.socketiochat.data.Resource
import com.purewowstudio.socketiochat.data.SocketChat.disconnect
import com.purewowstudio.socketiochat.data.SocketChat.initSocket
import com.purewowstudio.socketiochat.data.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class MainViewState {
    data class Success(val isLoading: Boolean, val message: String? = null) : MainViewState()
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainViewModel : ViewModel() {

    private val viewState = MutableLiveData<MainViewState>()
    val _state = viewState

    init {
        viewModelScope.launch {
            val value = initSocket()
            value.collect {
                onResult(it)
            }
        }
    }

    private fun onResult(resource: Resource<String>) {
        when (resource.status) {
            Status.SUCCESS -> viewState.value =
                MainViewState.Success(isLoading = false, message = resource.data)
            Status.ERROR -> viewState.value =
                MainViewState.Success(isLoading = false, message = resource.message)
            Status.LOADING -> viewState.value = MainViewState.Success(isLoading = true)
        }
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}
