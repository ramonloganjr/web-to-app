package com.webtoapp.core.billing

sealed class PurchaseState {
    object Idle : PurchaseState()
    object Loading : PurchaseState()
    data class Success(val message: String = "") : PurchaseState()
    data class Error(val message: String = "") : PurchaseState()
}
