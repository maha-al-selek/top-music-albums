package com.maha.topmusicalbums.ui.view.albumdetailscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@Composable
fun AlbumImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(data = imageUrl),
        contentDescription = "Album Art",
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentScale = ContentScale.FillBounds
    )
}