package com.purewowstudio.socketiochat.ui.screens.login

sealed class LoginViewState {
    data class Success(val isLoading: Boolean = false, val message: String? = null) : LoginViewState()
}