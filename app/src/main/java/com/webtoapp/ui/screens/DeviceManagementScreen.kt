package com.webtoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webtoapp.ui.viewmodel.CloudViewModel

@Composable
fun DeviceManagementScreen(
    cloudViewModel: CloudViewModel,
    onBack: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Device Management")
    }
}
