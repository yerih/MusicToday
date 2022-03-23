package com.architectcoders.musictoday.ui.main

import com.google.gson.annotations.SerializedName

data class ArtistSearch(
    @SerializedName("data")val artists: List<Artist>,
    val next: String,
    val total: Int
) {
    data class Artist(
        val id: String,
        val link: String,
        val name: String,
        val nb_album: Int,
        val nb_fan: Int,
        val picture: String,
        val picture_big: String,
        val picture_medium: String,
        val picture_small: String,
        val picture_xl: String,
        val radio: Boolean,
        val tracklist: String,
        val type: String
    )
}