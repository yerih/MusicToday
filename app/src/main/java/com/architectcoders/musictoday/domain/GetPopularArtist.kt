package com.architectcoders.musictoday.domain

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import kotlinx.coroutines.flow.Flow


class GetPopularArtistUseCase(private val repository: ArtistRepository) {
    operator fun invoke(): Flow<List<ArtistEntity>> = repository.artists
}