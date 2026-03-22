package com.webtoapp.ui.screens.community

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webtoapp.ui.viewmodel.CommunityViewModel

@Composable
fun FavoritesScreen(
    communityViewModel: CommunityViewModel,
    onBack: () -> Unit = {},
    onModuleClick: (Int) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Favorites")
    }
}
