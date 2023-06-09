package com.architectcoders.musictoday.ui.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.compose.asyncImageModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistItem(
    artist: Artist,
    onClick: (Artist) -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(horizontal = 2.5.dp, vertical = 2.5.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = 5.dp,
        onClick = { onClick(artist) }
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .clip(RoundedCornerShape(18.dp, 18.dp, 0.dp, 0.dp))
                    .fillMaxSize(),
                model = asyncImageModel(artist.imageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "artist cover",
                filterQuality = FilterQuality.Low
            )
            Text(
                text = artist.name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))
                    .background(Color.DarkGray)
            )
        }
    }
}

