package com.maha.topmusicalbums.ui.view.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maha.topmusicalbums.ui.state.State
import com.maha.topmusicalbums.ui.view.homescreen.components.AlbumsGrid
import com.maha.topmusicalbums.ui.view.homescreen.components.TopBar
import com.maha.topmusicalbums.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchAlbums(context)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onRefresh = { viewModel.fetchAlbums(context) })
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val dataState = uiState.value) {
                is State.Loading -> {
                    CircularProgressIndicator()
                }
                is State.Success -> dataState.data?.let { AlbumsGrid(albums = it, navController = navController) }
                is State.Error -> Text(text = dataState.message, fontSize = 20.sp)
            }
        }
    }
}