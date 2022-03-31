package com.architectcoders.musictoday.domain

import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.data.Error
import com.architectcoders.musictoday.database.ArtistEntity

class FavoriteToggleUseCase(private val repository: ArtistRepository) {
    suspend operator fun invoke(artist: ArtistEntity): Error? = repository.favoriteToggle(artist)
}