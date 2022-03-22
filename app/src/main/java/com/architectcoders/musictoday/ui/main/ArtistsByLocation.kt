package com.architectcoders.musictoday.ui.main

import com.google.gson.annotations.SerializedName

data class ArtistsByLocation(
    @SerializedName("topartists") val topArtists: TopArtists
) {
    data class TopArtists(
        @SerializedName("@attr") val attr: Attr,
        @SerializedName("artist")val artists: List<Artist>
    ) {
        data class Attr(
            val country: String,
            val page: String,
            val perPage: String,
            val total: String,
            val totalPages: String
        )

        data class Artist(
            val image: List<Image>,
            val listeners: String,
            val mbid: String,
            val name: String,
            val streamable: String,
            val url: String
        ) {
            data class Image(
                @SerializedName("#text")val text: String,
                val size: String
            )
        }
    }
}