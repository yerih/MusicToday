package com.architectcoders.musictoday.data.server

import arrow.core.Either
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import com.architectcoders.musictoday.ui.common.LocationDataSource
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import javax.inject.Inject


class ArtistServerDataSource @Inject constructor(
    private val locationHelper: LocationDataSource,
    private val remoteService: MusicService
) : ArtistRemoteDataSource {

    override suspend fun getPopularArtists(): Either<Error, List<Artist>> = tryCall {
        locationHelper.getCountryByGPS()?.let { country ->
            remoteService.getArtistByLocation(country).topArtists.artists
                .take(10)
                .map { artistLocation -> artistLocation.toDomainModel() }
        } ?: remoteService.getPopularArtists().artists.toDomainModel()
    }

    override suspend fun getArtistInfo(artist: Artist): Either<Error, Artist> = tryCall {
        remoteService.getArtistInfo(artist.name).artist.toDomainModel(artist)
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
