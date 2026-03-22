package com.webtoapp.core.stats

import android.content.Context

class WebsiteScreenshotService private constructor(private val context: Context) {
    fun hasScreenshot(id: Long): Boolean = false
    suspend fun captureScreenshot(id: Long, url: String) {}
    
    companion object {
        @Volatile private var instance: WebsiteScreenshotService? = null
        fun getInstance(context: Context): WebsiteScreenshotService =
            instance ?: synchronized(this) { instance ?: WebsiteScreenshotService(context).also { instance = it } }
    }
}
