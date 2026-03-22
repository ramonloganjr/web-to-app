package com.webtoapp.core.cloud

import android.content.Context

class AppDownloadManager private constructor(private val context: Context) {
    companion object {
        @Volatile private var instance: AppDownloadManager? = null
        fun getInstance(context: Context): AppDownloadManager =
            instance ?: synchronized(this) { instance ?: AppDownloadManager(context).also { instance = it } }
    }
}
