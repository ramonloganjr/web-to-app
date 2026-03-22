package com.webtoapp.core.stats

import com.webtoapp.data.dao.AppUsageStatsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class OverallStats(
    val totalApps: Int = 0,
    val totalLaunches: Int = 0,
    val totalDuration: Long = 0
)

class AppStatsRepository(private val dao: AppUsageStatsDao) {
    val allStats: Flow<List<Any>> = flowOf(emptyList())
    suspend fun getOverallStats(): OverallStats = OverallStats()
}
