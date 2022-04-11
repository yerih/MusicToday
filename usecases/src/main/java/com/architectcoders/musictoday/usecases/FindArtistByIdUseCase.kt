package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindArtistByIdUseCase @Inject constructor(private val repository: ArtistRepository) {
    operator fun invoke(artistId: Int): Flow<Artist> = repository.findById(artistId)
}