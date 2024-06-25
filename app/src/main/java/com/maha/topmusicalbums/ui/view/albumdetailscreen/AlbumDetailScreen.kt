package com.maha.topmusicalbums.ui.view.albumdetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maha.topmusicalbums.ui.state.State
import com.maha.topmusicalbums.ui.view.albumdetailscreen.components.AlbumDetailsFooter
import com.maha.topmusicalbums.ui.view.albumdetailscreen.components.AlbumImage
import com.maha.topmusicalbums.ui.view.albumdetailscreen.components.AlbumInfo
import com.maha.topmusicalbums.ui.view.albumdetailscreen.components.BackButton
import com.maha.topmusicalbums.ui.viewmodel.AlbumDetailViewModel

@Composable
fun AlbumDetailScreen(albumId: Int?, viewModel: AlbumDetailViewModel = hiltViewModel(), navController: NavController) {
    if (albumId == null) {
        Text("Album not found")
        return
    }

    LaunchedEffect(albumId) {
        viewModel.fetchAlbumById(albumId)
    }

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (val state = uiState.value) {
        is State.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is State.Success -> {
            val album = state.data!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box {
                    AlbumImage(album.albumThumbnail)
                    BackButton(onBackPressed = { navController.navigateUp() })
                }
                Spacer(modifier = Modifier.height(16.dp))
                AlbumInfo(
                    name = album.name,
                    artistName = album.artistName,
                    genres = album.genre.map { it.name } // Passing list of genre names
                )
                Spacer(modifier = Modifier.weight(1f))
                AlbumDetailsFooter(
                    releaseDate = album.releaseDate,
                    copyright = album.copyright,
                    url = album.url,
                    context = context
                )
            }
        }
        is State.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
