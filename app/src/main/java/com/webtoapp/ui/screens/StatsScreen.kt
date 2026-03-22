package com.webtoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webtoapp.core.stats.OverallStats
import com.webtoapp.data.model.WebApp

@Composable
fun StatsScreen(
    apps: List<WebApp> = emptyList(),
    allStats: List<Any> = emptyList(),
    healthRecords: List<Any> = emptyList(),
    overallStats: OverallStats = OverallStats(),
    onBack: () -> Unit = {},
    onCheckHealth: (WebApp) -> Unit = {},
    onCheckAllHealth: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Stats")
    }
}
