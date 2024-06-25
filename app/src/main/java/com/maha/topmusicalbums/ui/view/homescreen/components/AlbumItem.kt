package com.maha.topmusicalbums.ui.view.homescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.maha.topmusicalbums.model.db.RealmAlbum
import com.maha.topmusicalbums.navigation.Screen


@Composable
fun AlbumItem(album: RealmAlbum, navController: NavController) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable { navController.navigate(Screen.AlbumDetail.createRoute(album.id)) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = album.albumThumbnail),
            contentDescription = "Album Art",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.1f))
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = album.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp
                ),
                maxLines = 1,
                modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp)
            )
            Text(
                text = album.artistName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.LightGray,
                    fontSize = 12.sp
                ),
                maxLines = 1,
                modifier = Modifier.padding(5.dp,0.dp,0.dp,0.dp)
            )
        }
    }
}