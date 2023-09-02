package com.architectcoders.musictoday.data.server.musicovery

import com.architectcoders.musictoday.domain.Artist

data class SearchMusicoveryResponse(
    val artists: ArtistsMusicovery
){


    fun asArtistDomain(): List<Artist> = artists.artist.map {
        with(it.artists.artist){
            return@with Artist(
                name = name,
                mbid = mbid,
                genre = genre
            )
        }
    }

    data class ArtistsMusicovery(
        val artist: List<ArtistMusicovery>
    )

    data class ArtistMusicovery(
        val artists: ArtistsXMusicovery,
        val match: String
    )

    data class ArtistsXMusicovery(
        val artist: ArtistX
    )

    data class ArtistX(
        val country: String,
        val decade: String,
        val genre: String,
        val mbid: String,
        val name: String
    )
}






