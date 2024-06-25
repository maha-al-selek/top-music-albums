package com.maha.topmusicalbums.ui.view.albumdetailscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(onBackPressed: () -> Unit) {
    IconButton(
        onClick = { onBackPressed() },
        modifier = Modifier
            .padding(16.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.7f))
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
    }
}