package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error

class FavoriteToggleUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(artist: Artist): Error? = repository.favoriteToggle(artist)
}