package com.webtoapp.ui.screens.aimodule

import androidx.compose.runtime.Composable
import com.webtoapp.core.extension.ExtensionModule

@Composable
fun AiModuleDeveloperScreen(
    onNavigateBack: () -> Unit,
    onModuleCreated: (ExtensionModule) -> Unit,
    onNavigateToAiSettings: () -> Unit = {}
) {
    AiModuleDeveloperScreenRefactored(
        onNavigateBack = onNavigateBack,
        onModuleCreated = onModuleCreated,
        onNavigateToAiSettings = onNavigateToAiSettings
    )
}
