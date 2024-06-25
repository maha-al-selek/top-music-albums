package com.maha.topmusicalbums.ui.view.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maha.topmusicalbums.model.db.RealmAlbum

@Composable
fun AlbumsGrid(albums: List<RealmAlbum>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albums.size) { index ->
            AlbumItem(album = albums[index], navController = navController)
        }
    }
}

