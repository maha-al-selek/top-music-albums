package com.maha.topmusicalbums.ui.view.albumdetailscreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenreItem(genre: String) {
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFF007AFF), shape = RoundedCornerShape(50))
            .padding(5.dp)
    ) {
        Text(
            text = genre,
            modifier = Modifier
                .padding(6.dp,5.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF007AFF), // Adjust the color to match your design
                fontSize = 12.sp,
            )
        )
    }
}
