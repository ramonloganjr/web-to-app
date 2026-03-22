package com.webtoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webtoapp.core.cloud.AppDownloadManager
import com.webtoapp.ui.viewmodel.CloudViewModel

@Composable
fun AppStoreScreen(
    cloudViewModel: CloudViewModel,
    onInstallModule: (String) -> Unit = {},
    downloadManager: AppDownloadManager? = null
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("App Store")
    }
}
