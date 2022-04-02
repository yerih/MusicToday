package com.architectcoders.musictoday.data

import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import kotlinx.coroutines.flow.Flow



class ArtistRepository(
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
            println("hola: arg: name= ${artist.name}, publis= ${artist.publishingDate}, img= ${artist.imageUrl}")
            val artistInfo = remoteDataSource.getArtistInfo(artist.name)
            artistInfo.fold(
                { return it },
                {
                    val artistUpdate = artist.copy(biography = it.biography, publishingDate = it.publishingDate)
                    println("hola: name= ${artistUpdate.name}, publis= ${artistUpdate.publishingDate}, img= ${artistUpdate.imageUrl}")
//                    println("hola: name= ${it.name}, publis= ${it.publishingDate}, img= ${it.imageUrl}")
                    localDataSource.save(listOf(artistUpdate))
                }
            )
        }
        return null
    }

    suspend fun favoriteToggle(artist: Artist): Error? {
        val artistUpdate = artist.copy(favorite = !artist.favorite)
        return localDataSource.save(listOf(artistUpdate))
    }

}



