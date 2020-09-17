package com.purewowstudio.socketiochat.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewowstudio.socketiochat.data.Result
import com.purewowstudio.socketiochat.domain.chat.SocketChat.disconnect
import com.purewowstudio.socketiochat.domain.chat.SocketChat.initSocket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainViewModel : ViewModel() {

    private val viewState = MutableLiveData<MainViewState>()
    val _state: LiveData<MainViewState> = viewState

    init {
        viewModelScope.launch {
            val value = initSocket()
            value.collect {
                onResult(it)
            }
        }
    }

    private fun onResult(result: Result<String>) {
        when (result) {
            is Result.Success -> viewState.value =
                MainViewState.Success(message = result.data)
            is Result.Error -> viewState.value =
                MainViewState.Success(message = result.message)
            is Result.Loading -> viewState.value = MainViewState.Success(isLoading = true)
        }
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}
