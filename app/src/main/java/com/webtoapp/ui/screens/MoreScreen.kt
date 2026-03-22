package com.webtoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MoreScreen(
    onOpenAiCoding: () -> Unit = {},
    onOpenAiSettings: () -> Unit = {},
    onOpenThemeSettings: () -> Unit = {},
    onOpenBrowserKernel: () -> Unit = {},
    onOpenHostsAdBlock: () -> Unit = {},
    onOpenAppModifier: () -> Unit = {},
    onOpenExtensionModules: () -> Unit = {},
    onOpenLinuxEnvironment: () -> Unit = {},
    onOpenRuntimeDeps: () -> Unit = {},
    onOpenPortManager: () -> Unit = {},
    onOpenStats: () -> Unit = {},
    onOpenAbout: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("More")
    }
}
