package com.architectcoders.musictoday.data.server

import arrow.core.Either
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.musicovery.MusicoveryService
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Response
import com.architectcoders.musictoday.ui.common.LocationDataSource
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.Error
import com.architectcoders.musictoday.domain.Error as ErrorDomain


class ArtistServerDataSource @Inject constructor(
    private val locationHelper: LocationDataSource,
    private val remoteService: MusicService,
    private val musicoveryService: MusicoveryService,
) : ArtistRemoteDataSource {

    override suspend fun getPopularArtists(): Either<ErrorDomain, List<Artist>> = tryCall {
        locationHelper.getCountryByGPS()?.let { country ->
            remoteService.getArtistByLocation(country).topArtists.artists
                .take(10)
                .map { artistLocation -> artistLocation.toDomainModel() }
        } ?: remoteService.getPopularArtists().artists.toDomainModel()
    }

    override suspend fun getArtistInfo(artist: Artist): Either<ErrorDomain, Artist> = tryCall {
        remoteService.getArtistInfo(artist.name).artist.toDomainModel(artist)
    }

    override suspend fun getSimilarArtists(artist: String): Response<List<Artist>> {
        return try {
            val result = musicoveryService.searchMusicovery(artist)
            Response(result.value?.asArtistDomain())
        }catch (http: HttpException){
            Response(error = Error(http.message), value = null)
        }catch (e: Exception){
            Response(error = Error(e.message), value = null)
        }
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
