package com.maha.topmusicalbums.ui.view.albumdetailscreen.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AlbumDetailsFooter(releaseDate: String, copyright: String, url: String, context: android.content.Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Released $releaseDate",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = copyright,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007AFF), // Background color
                contentColor = MaterialTheme.colorScheme.onPrimary // Text color
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Visit The Album")
        }
    }
}
