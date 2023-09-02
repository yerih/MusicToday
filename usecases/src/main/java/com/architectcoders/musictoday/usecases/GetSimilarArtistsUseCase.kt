package com.architectcoders.musictoday.usecases

import com.architectcoders.musictoday.data.ArtistRepository
import javax.inject.Inject

class GetSimilarArtistsUseCase @Inject constructor(
    private val repository: ArtistRepository
){
    suspend operator fun invoke(artist: String) = repository.getSimilarArtists(artist)
}

