package com.webtoapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CreateNodeJsAppScreen(
    existingAppId: Long? = null,
    onBack: () -> Unit = {},
    onCreated: (String, Any?, Uri?, String) -> Unit = { _, _, _, _ -> }
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Create Node.js App")
    }
}
