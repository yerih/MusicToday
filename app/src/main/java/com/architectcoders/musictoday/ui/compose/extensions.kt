package com.architectcoders.musictoday.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.google.gson.Gson

@Composable
fun asyncImageModel(url: String? = null): ImageRequest {
    return ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build()
}

fun <T>T.toJson(): String = Gson().toJson(this)
fun <T>String.fromJson(type: Class<T>): T = Gson().fromJson(this, type)


