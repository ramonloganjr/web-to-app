package com.webtoapp.core.auth

sealed class AuthState {
    object LoggedOut : AuthState()
    data class LoggedIn(val username: String = "", val email: String = "") : AuthState()
}
