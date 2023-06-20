package com.architectcoders.musictoday.ui.compose.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.compose.asyncImageModel
import com.architectcoders.musictoday.ui.compose.ui_components.AppBar
import com.architectcoders.musictoday.ui.detail.DetailViewModel


@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClicked: (()->Unit)? = null
){
    val artist = viewModel.state.artist
    Scaffold(
        topBar = { AppBar(title = artist?.name?:"loading...", onBackClicked = onBackClicked) },
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .fillMaxSize(),
                model = asyncImageModel(artist?.imageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "artist cover",
                filterQuality = FilterQuality.Low
            )
            Detail(artist)
        }
    }
}

@Composable
private fun Detail(artist: Artist? = null){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(stringResource(id = R.string.Published))
        Text(artist?.publishingDate.toString())
        Text(stringResource(R.string.Biography))
        Text(artist?.biography.toString())
    }
}

