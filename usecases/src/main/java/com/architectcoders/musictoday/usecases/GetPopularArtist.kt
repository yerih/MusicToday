package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow


class GetPopularArtistUseCase(private val repository: ArtistRepository) {
    operator fun invoke(): Flow<List<Artist>> = repository.artists
}