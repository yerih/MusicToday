package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.domain.Error

class RequestArtistsUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(): Error? = repository.requestArtists()
}