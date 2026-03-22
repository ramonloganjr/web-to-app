package com.webtoapp.core.auth

import android.content.Context

class TokenManager private constructor(context: Context) {
    companion object {
        @Volatile private var instance: TokenManager? = null
        fun getInstance(context: Context): TokenManager =
            instance ?: synchronized(this) { instance ?: TokenManager(context).also { instance = it } }
    }
}
