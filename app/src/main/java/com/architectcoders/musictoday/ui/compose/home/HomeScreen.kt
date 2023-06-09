package com.architectcoders.musictoday.ui.compose.home

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.compose.ui_components.AppBar
import com.architectcoders.musictoday.ui.compose.ui_components.LoadingScreen


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (Artist) -> Unit = {},
){
    Scaffold(
        topBar = { AppBar(title = stringResource(id = R.string.home_screen)) },
    ) { padding ->
        LoadingScreen(viewModel.state.loading)
        LazyVerticalGrid(
            contentPadding = padding,
            columns = GridCells.Adaptive(150.dp)
        ){
            viewModel.state.artists?.let{ artists ->
                items(artists.size){
                    ArtistItem(artists[it], onItemClicked)
                }
            }
        }
    }
}

