package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.domain.Error
import javax.inject.Inject

class RequestArtistsUseCase @Inject constructor(private val repository: ArtistRepository) {
    suspend operator fun invoke(): Error? = repository.requestArtists()
}