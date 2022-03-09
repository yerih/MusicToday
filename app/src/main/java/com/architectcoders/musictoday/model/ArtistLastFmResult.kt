package com.architectcoders.musictoday.model

import com.google.gson.annotations.SerializedName


data class ArtistLastFmResult(
    val artists: Artists
) {
    data class Artists(
        @SerializedName("@attr") val attr: Attr,
        val artist: List<Artist>
    ) {
        data class Attr(
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
            val playcount: String,
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


