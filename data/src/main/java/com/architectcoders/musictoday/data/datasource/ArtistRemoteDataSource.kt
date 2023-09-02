package com.architectcoders.musictoday.data.datasource

import arrow.core.Either
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import com.architectcoders.musictoday.domain.Response


interface ArtistRemoteDataSource {
    suspend fun getPopularArtists(): Either<Error, List<Artist>>
    suspend fun getArtistInfo(artist: Artist): Either<Error, Artist>

    suspend fun getSimilarArtists(artist: String): Response<List<Artist>>
}