package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow

class FindArtistByIdUseCase(private val repository: ArtistRepository) {
    operator fun invoke(artistId: Int): Flow<Artist> = repository.findById(artistId)
}