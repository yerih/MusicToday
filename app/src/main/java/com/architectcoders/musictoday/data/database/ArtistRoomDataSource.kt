package com.architectcoders.musictoday.data.database

import com.architectcoders.musictoday.data.server.PopularArtists
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.server.tryCall
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map



class ArtistRoomDataSource(private val artistDao: ArtistDao) : ArtistLocalDataSource {
    override val artists: Flow<List<Artist>> = artistDao.getAll().map { it.toDomainModel() }
    override fun findById(id: Int): Flow<Artist> = artistDao.findById(id).map { it.toDomainModel() }
    override suspend fun isEmpty(): Boolean = artistDao.artistCount() == 0
    override suspend fun save(artists: List<Artist>): Error? = tryCall{
        artistDao.insertArtists(artists.fromDomainModel())
    }.fold({it}, {null})
}



private fun List<PopularArtists.Artist>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
private fun PopularArtists.Artist.toDomainModel(): Artist = Artist(
        name = name,
        imageUrl = picture_medium,
)
private fun List<Artist>.fromDomainModel(): List<ArtistEntity> = map { it.fromDomainModel() }
private fun Artist.fromDomainModel(): ArtistEntity = ArtistEntity(
    id, name, biography, publishingDate, imageUrl, favorite
)