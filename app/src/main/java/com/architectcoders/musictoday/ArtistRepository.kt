package com.architectcoders.musictoday

import android.R
import android.util.Log
import com.architectcoders.musictoday.database.ArtistDao
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.ArtistInfo
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.common.LocationManager
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class ArtistRepository(app: App) {

    private val localDataSource = ArtistLocalDataSource(app.db.ArtistDao())
    private val remoteDataSource = ArtistRemoteDataSource(LocationHelper(app))

    val artists = localDataSource.artists

    fun findById(id: Int): Flow<ArtistEntity> = localDataSource.findById(id)

    suspend fun getArtists() {
        if (localDataSource.isEmpty()) {
            val artists = remoteDataSource.getPopularArtists()
            localDataSource.save(artists)
        }
    }

    suspend fun getArtistInfo(artist: ArtistEntity) {
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

    suspend fun favoriteToggle(artist: ArtistEntity){
        val artistUpdate = artist.copy(favorite = !artist.favorite)
        localDataSource.save(listOf(artistUpdate))
    }

}

class ArtistLocalDataSource(private val artistDao: ArtistDao) {
    val artists: Flow<List<ArtistEntity>> = artistDao.getAll()
    fun findById(id: Int): Flow<ArtistEntity> = artistDao.findById(id)
    suspend fun isEmpty(): Boolean = artistDao.artistCount() == 0
    suspend fun save(artists: List<ArtistEntity>) = artistDao.insertArtists(artists)
    suspend fun updateArtist(artist: ArtistEntity) = artistDao.updateArtist(artist)
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

fun ArtistInfo.Artist.toLocalArtist(): ArtistEntity = ArtistEntity(
    name = name,
    biography = bio.summary,
    publishingDate = bio.published,
)

fun ArtistInfo.Artist.toLocalArtist(imageUrl: String): ArtistEntity = ArtistEntity(
    name = name,
    biography = bio.summary,
    publishingDate = bio.published,
    imageUrl = imageUrl
)

fun PopularArtists.Artist.toLocalArtist(): ArtistEntity = ArtistEntity(
    id,
    name = name,
    biography = "",
    publishingDate = "",
    imageUrl = picture_medium,
)

