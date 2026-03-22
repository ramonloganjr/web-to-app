package com.webtoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.webtoapp.core.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object LoggedOut : AuthState()
    data class LoggedIn(val username: String = "", val email: String = "") : AuthState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.LoggedOut)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
}
