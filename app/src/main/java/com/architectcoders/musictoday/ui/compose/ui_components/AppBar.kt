package com.architectcoders.musictoday.ui.compose.ui_components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.architectcoders.musictoday.R

@Composable
fun AppBar(
    title: String? = stringResource(id = R.string.app_name),
    actions: () -> Unit = {},
    onBackClicked: (() -> Unit)? = null,
){
    TopAppBar(
        title = { Text(text = title?:"loading...") },
        navigationIcon = { onBackClicked?.let { IconButtonBar(onClick = onBackClicked) } },
        actions = { actions() },
    )
}


