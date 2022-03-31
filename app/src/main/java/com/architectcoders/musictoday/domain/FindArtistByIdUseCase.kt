package com.architectcoders.musictoday.domain

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import kotlinx.coroutines.flow.Flow

class FindArtistByIdUseCase(private val repository: ArtistRepository) {
    operator fun invoke(artistId: Int): Flow<ArtistEntity> = repository.findById(artistId)
}