package com.purewowstudio.socketiochat.ui.screens.main

sealed class MainViewState {
    data class Success(val isLoading: Boolean = false, val message: String? = null) : MainViewState()
}