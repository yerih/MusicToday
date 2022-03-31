package com.architectcoders.musictoday.data.datasource

import com.architectcoders.musictoday.data.MusicService
import com.architectcoders.musictoday.data.PopularArtists
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.main.ArtistsByLocation

class ArtistRemoteDataSource(private val locationHelper: LocationHelper) {

    suspend fun getPopularArtists(): List<Artist> =
        locationHelper.getCountryByGPS()?.let { country ->
            MusicService.service.getArtistByLocation(country).topArtists.artists
                .take(10)
                .map { artistLocation -> artistLocation.toDomainModel() }
        } ?: MusicService.service.getPopularArtists().artists.toDomainModel()

    suspend fun getArtistInfo(name: String) = MusicService.service.getArtistInfo(name)

}

private fun List<PopularArtists.Artist>.toDomainModel(): List<Artist> = map { it.toDomainModel() }
private fun PopularArtists.Artist.toDomainModel(): Artist = Artist(name = name, imageUrl = picture_medium)
private fun ArtistsByLocation.TopArtists.Artist.toDomainModel(): Artist = Artist(name = name)
