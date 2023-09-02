package com.architectcoders.musictoday.domain

data class Artist(
    val id: Int = 0,
    val name: String = "",
    val biography: String = "",
    val publishingDate: String = "",
    val imageUrl: String = "",
    val favorite: Boolean = false,
    val mbid: String = "",
    val genre: String = "",
)


