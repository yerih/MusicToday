package com.architectcoders.musictoday.data

import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ArtistRepository @Inject constructor(
    private val localDataSource: ArtistLocalDataSource,
    private val remoteDataSource: ArtistRemoteDataSource
) {

    val artists = localDataSource.artists

    fun findById(id: Int): Flow<Artist> = localDataSource.findById(id)

    suspend fun requestArtists(): Error? {
        if (localDataSource.isEmpty()) {
            remoteDataSource.getPopularArtists().fold({ return it }, { localDataSource.save(it) })
        }
        return null
    }

    suspend fun getArtistInfo(artist: Artist): Error? {
        if (artist.biography.isEmpty()) {
            val artistInfo = remoteDataSource.getArtistInfo(artist)
            artistInfo.fold(
                { return it },
                { localDataSource.save(listOf(it.copy())) }
            )
        }
        return null
    }

    suspend fun favoriteToggle(artist: Artist): Error? {
        val artistUpdate = artist.copy(favorite = !artist.favorite)
        return localDataSource.save(listOf(artistUpdate))
    }

}



