package com.architectcoders.musictoday.data.datasource

import com.architectcoders.musictoday.framework.server.ArtistInfo
import com.architectcoders.musictoday.domain.Artist

interface ArtistRemoteDataSource {
    suspend fun getPopularArtists(): List<Artist>
    suspend fun getArtistInfo(name: String): ArtistInfo
}