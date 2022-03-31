package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.data.Error
import com.architectcoders.musictoday.domain.Artist

class GetArtistInfoUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(artist: Artist): Error? = repository.getArtistInfo(artist)
}