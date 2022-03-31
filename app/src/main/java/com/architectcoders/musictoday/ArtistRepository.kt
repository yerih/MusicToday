package com.architectcoders.musictoday

import com.architectcoders.musictoday.data.*
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.common.LocationHelper
import kotlinx.coroutines.flow.Flow

class ArtistRepository(app: App) {

    private val localDataSource = ArtistLocalDataSource(app.db.ArtistDao())
    private val remoteDataSource = ArtistRemoteDataSource(LocationHelper(app))

    val artists = localDataSource.artists

    fun findById(id: Int): Flow<Artist> = localDataSource.findById(id)

    suspend fun requestArtists(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val artists = remoteDataSource.getPopularArtists()
            localDataSource.save(artists)
        }
    }

    suspend fun getArtistInfo(artist: Artist): Error? = tryCall {
        if (artist.biography.isEmpty()) {
            val artistInfo = remoteDataSource.getArtistInfo(artist.name).artist
            val artistUpdate = artist.copy(
                publishingDate = artistInfo.bio.published,
                biography = artistInfo.bio.summary,
                imageUrl = artist.imageUrl
            )
            localDataSource.save(listOf(artistUpdate))
        }
    }

    suspend fun favoriteToggle(artist: Artist): Error? = tryCall {
        val artistUpdate = artist.copy(favorite = !artist.favorite)
        localDataSource.save(listOf(artistUpdate))
    }

}



