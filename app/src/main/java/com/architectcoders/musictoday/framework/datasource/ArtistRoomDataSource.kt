package com.architectcoders.musictoday.framework.datasource

import com.architectcoders.musictoday.data.PopularArtists
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.framework.database.ArtistDao
import com.architectcoders.musictoday.framework.database.ArtistEntity
import com.architectcoders.musictoday.framework.database.toDomainModel
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class ArtistRoomDataSource(private val artistDao: ArtistDao) : ArtistLocalDataSource {
    override val artists: Flow<List<Artist>> = artistDao.getAll().map { it.toDomainModel() }
    override fun findById(id: Int): Flow<Artist> = artistDao.findById(id).map { it.toDomainModel() }
    override suspend fun isEmpty(): Boolean = artistDao.artistCount() == 0
    override suspend fun save(artists: List<Artist>) = artistDao.insertArtists(artists.fromDomainModel())
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