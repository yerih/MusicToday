package com.architectcoders.musictoday

import com.architectcoders.musictoday.database.ArtistDao
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ArtistRepository(app: App) {

    private val localDataSource = ArtistLocalDataSource(app.db.ArtistDao())
    private val remoteLocalDataSource = ArtistRemoteLocalDataSource()

    val artists = localDataSource.artists

    suspend fun getArtistsFromRepository() = withContext(Dispatchers.IO){
        if (localDataSource.isEmpty()) {
            val artists = remoteLocalDataSource.getPopularArtists().toLocalArtists()
            localDataSource.save(artists)
        }
    }
}


class ArtistLocalDataSource(private val artistDao: ArtistDao) {
    val artists: Flow<List<ArtistEntity>> = artistDao.getAll()
    fun isEmpty(): Boolean = artistDao.artistCount() == 0
    fun save(artists: List<ArtistEntity>) = artistDao.insertArtists(artists)
}

class ArtistRemoteLocalDataSource() {

    suspend fun getPopularArtists(): List<PopularArtists.Artist> {
        return MusicService.service.getPopularArtists().artists
    }
}

fun List<PopularArtists.Artist>.toLocalArtists(): List<ArtistEntity> = map { it.toLocalArtist() }

fun PopularArtists.Artist.toLocalArtist(): ArtistEntity = ArtistEntity(
    id,
    name = name,
    biography = "",
    publishingDate = "",
    imageUrl = "",
)

