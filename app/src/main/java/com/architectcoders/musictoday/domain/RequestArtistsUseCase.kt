package com.architectcoders.musictoday.domain

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.data.Error

class RequestArtistsUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(): Error? = repository.requestArtists()
}