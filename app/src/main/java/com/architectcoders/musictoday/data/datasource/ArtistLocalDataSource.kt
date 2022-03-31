package com.architectcoders.musictoday.data.datasource

import com.architectcoders.musictoday.data.PopularArtists
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.database.ArtistEntity
import com.architectcoders.musictoday.data.database.toDomainModel
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ArtistLocalDataSource(private val artistDao: ArtistDao) {
    val artists: Flow<List<Artist>> = artistDao.getAll().map { it.toDomainModel() }
    fun findById(id: Int): Flow<Artist> = artistDao.findById(id).map { it.toDomainModel() }
    suspend fun isEmpty(): Boolean = artistDao.artistCount() == 0
    suspend fun save(artists: List<Artist>) = artistDao.insertArtists(artists.fromDomainModel())
}



private fun List<PopularArtists.Artist>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
private fun PopularArtists.Artist.toDomainModel(): Artist = Artist(
    id,
    name = name,
    biography = "",
    publishingDate = "",
    imageUrl = picture_medium,
)
private fun List<Artist>.fromDomainModel(): List<ArtistEntity> = map { it.fromDomainModel() }
private fun Artist.fromDomainModel(): ArtistEntity = ArtistEntity(
    id, name, biography, publishingDate, imageUrl, favorite
)