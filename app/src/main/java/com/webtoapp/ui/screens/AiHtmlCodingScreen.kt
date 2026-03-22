package com.webtoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webtoapp.core.ai.htmlcoding.ProjectFile

@Composable
fun AiHtmlCodingScreen(
    onBack: () -> Unit = {},
    onExportToHtmlProject: (List<ProjectFile>, String) -> Unit = { _, _ -> },
    onNavigateToAiSettings: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("AI HTML Coding")
    }
}
