package com.architectcoders.musictoday

import com.architectcoders.musictoday.database.ArtistDao
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.data.*
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import kotlinx.coroutines.flow.Flow

class ArtistRepository(app: App) {

    private val localDataSource = ArtistLocalDataSource(app.db.ArtistDao())
    private val remoteDataSource = ArtistRemoteDataSource(LocationHelper(app))

    val artists = localDataSource.artists

    fun findById(id: Int): Flow<ArtistEntity> = localDataSource.findById(id)

    suspend fun requestArtists(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val artists = remoteDataSource.getPopularArtists()
            localDataSource.save(artists)
        }
    }

    suspend fun getArtistInfo(artist: ArtistEntity): Error? = tryCall {
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

    suspend fun favoriteToggle(artist: ArtistEntity): Error? = tryCall {
        val artistUpdate = artist.copy(favorite = !artist.favorite)
        localDataSource.save(listOf(artistUpdate))
    }

}

class ArtistLocalDataSource(private val artistDao: ArtistDao) {
    val artists: Flow<List<ArtistEntity>> = artistDao.getAll()
    fun findById(id: Int): Flow<ArtistEntity> = artistDao.findById(id)
    suspend fun isEmpty(): Boolean = artistDao.artistCount() == 0
    suspend fun save(artists: List<ArtistEntity>) = artistDao.insertArtists(artists)
}

class ArtistRemoteDataSource(private val locationHelper: LocationHelper) {

    suspend fun getPopularArtists(): List<ArtistEntity> =
        locationHelper.getCountryByGPS()?.let { country ->
        MusicService.service.getArtistByLocation(country).topArtists
            .artists.take(10).map { artistMap -> artistMap.toLocalArtist() }
    } ?: MusicService.service.getPopularArtists().artists.toLocalArtists()

    suspend fun getArtistInfo(name: String) = MusicService.service.getArtistInfo(name)

}

fun List<PopularArtists.Artist>.toLocalArtists(): List<ArtistEntity> = map { it.toLocalArtist() }

fun ArtistsByLocation.TopArtists.Artist.toLocalArtist(): ArtistEntity = ArtistEntity(name = name)

fun PopularArtists.Artist.toLocalArtist(): ArtistEntity = ArtistEntity(
    id,
    name = name,
    biography = "",
    publishingDate = "",
    imageUrl = picture_medium,
)

