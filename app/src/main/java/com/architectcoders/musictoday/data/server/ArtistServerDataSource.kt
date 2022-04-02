package com.architectcoders.musictoday.data.server

import arrow.core.Either
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.main.ArtistsByLocation



class ArtistServerDataSource(private val locationHelper: LocationHelper) : ArtistRemoteDataSource {

    override suspend fun getPopularArtists(): Either<Error, List<Artist>> = tryCall {
        locationHelper.getCountryByGPS()?.let { country ->
            MusicService.service.getArtistByLocation(country).topArtists.artists
                .take(10)
                .map { artistLocation -> artistLocation.toDomainModel() }
        } ?: MusicService.service.getPopularArtists().artists.toDomainModel()
    }

    override suspend fun getArtistInfo(artist: Artist): Either<Error, Artist> = tryCall {
        MusicService.service.getArtistInfo(artist.name).artist.toDomainModel(artist)
    }

}

private fun ArtistInfo.Artist.toDomainModel(artist: Artist): Artist = artist.copy(
    name = name,
    biography = bio.summary,
    publishingDate = bio.published,
)
private fun ArtistInfo.Artist.toDomainModel(): Artist = Artist(
    name = name,
    biography = bio.summary,
    publishingDate = bio.published)
private fun List<PopularArtists.Artist>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
private fun PopularArtists.Artist.toDomainModel(): Artist = Artist(name = name, imageUrl = picture_medium)
private fun ArtistsByLocation.TopArtists.Artist.toDomainModel(): Artist = Artist(name = name)
