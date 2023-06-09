package com.architectcoders.musictoday.ui.compose.ui_components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButtonBar(
    imageVector: ImageVector = Icons.Default.ArrowBack,
    onClick:     () -> Unit = {}
){
    IconButton(onClick = onClick){
        Icon(imageVector = imageVector, contentDescription = "arrow back")
    }
}


