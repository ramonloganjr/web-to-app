package com.webtoapp.core.cloud

import com.webtoapp.core.auth.TokenManager

class CloudRepository(
    private val apiClient: CloudApiClient,
    private val tokenManager: TokenManager
)
