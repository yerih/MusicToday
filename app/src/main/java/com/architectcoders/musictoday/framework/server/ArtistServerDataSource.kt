package com.architectcoders.musictoday.framework.server

import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.main.ArtistsByLocation



class ArtistServerDataSource(private val locationHelper: LocationHelper) : ArtistRemoteDataSource {

    override suspend fun getPopularArtists(): List<Artist> =
        locationHelper.getCountryByGPS()?.let { country ->
            MusicService.service.getArtistByLocation(country).topArtists.artists
                .take(10)
                .map { artistLocation -> artistLocation.toDomainModel() }
        } ?: MusicService.service.getPopularArtists().artists.toDomainModel()

    override suspend fun getArtistInfo(name: String) = MusicService.service.getArtistInfo(name)

}

private fun List<PopularArtists.Artist>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
private fun PopularArtists.Artist.toDomainModel(): Artist = Artist(name = name, imageUrl = picture_medium)
private fun ArtistsByLocation.TopArtists.Artist.toDomainModel(): Artist = Artist(name = name)
