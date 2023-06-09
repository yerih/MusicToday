package com.architectcoders.musictoday.ui.compose.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.architectcoders.musictoday.di.ArtistId
import com.architectcoders.musictoday.di.Id
import com.architectcoders.musictoday.ui.compose.ui_components.AppBar
import com.architectcoders.musictoday.ui.detail.DetailViewModel


@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClicked: (()->Unit)? = null
){
    val artistName = viewModel.state.artist?.name
    Scaffold(
        topBar = { AppBar(title = artistName, onBackClicked = onBackClicked) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ){
            Text("Detail Screen")
        }
    }
}

