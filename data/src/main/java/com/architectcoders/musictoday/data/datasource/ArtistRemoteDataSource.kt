package com.architectcoders.musictoday.data.datasource

import arrow.core.Either
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error


interface ArtistRemoteDataSource {
    suspend fun getPopularArtists(): Either<Error, List<Artist>>
    suspend fun getArtistInfo(name: String): Either<Error, Artist>
}