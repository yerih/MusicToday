package com.architectcoders.appTestShared

import android.location.Location
import com.architectcoders.musictoday.data.database.ArtistDao
import com.architectcoders.musictoday.data.database.toDomainModel
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.data.server.ArtistInfo
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.server.MusicService
import com.architectcoders.musictoday.data.server.PopularArtists
import com.architectcoders.musictoday.data.database.ArtistEntity as ArtistDB
import com.architectcoders.musictoday.ui.common.LocationDataSource
import com.architectcoders.musictoday.ui.main.ArtistSearch
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.MutableStateFlow



class FakeArtistDao(artists: List<ArtistDB> = emptyList()) : ArtistDao {

    private val inMemoryArtists = MutableStateFlow(artists)
    private lateinit var findArtistFlow: MutableStateFlow<ArtistDB>


    override fun getAll(): Flow<List<ArtistDB>> = inMemoryArtists
    override fun findById(id: Int): Flow<ArtistDB> = MutableStateFlow(inMemoryArtists.value.first{id == it.id}).also { findArtistFlow = it }
    override suspend fun artistCount(): Int = inMemoryArtists.value.size
    override suspend fun insertArtists(artists: List<ArtistDB>) {
        inMemoryArtists.value = artists
        if(::findArtistFlow.isInitialized)
            artists.firstOrNull{it.id == findArtistFlow.value.id}?.let{ findArtistFlow.value = it}

    }
    override suspend fun updateArtist(artist: ArtistDB) {}

}


class FakeLocationHelper(country: String? = null): LocationDataSource{
    private var location: String? = country
//    var location = "US"
    override suspend fun findLastLocation(): Location? = null
    override suspend fun getCountryByGPS(): String? = location
}

class FakeRemoteService(private val artists: List<ArtistDB> = emptyList()): MusicService{
    override suspend fun getPopularArtists() = PopularArtists(
        artists = artists.map { PopularArtists.Artist(id = it.id, name = it.name) },
        total = artists.size
    )

    override suspend fun getArtistInfo(artist: String) = ArtistInfo(artist = ArtistInfo.Artist(name = artist))

    override suspend fun getArtistByLocation(country: String): ArtistsByLocation {
        val topArtists = ArtistsByLocation.TopArtists(
            ArtistsByLocation.TopArtists.Attr(country),
            artists.map { ArtistsByLocation.TopArtists.Artist(name = it.name) }
        )
        return ArtistsByLocation(topArtists)
    }

    override suspend fun getSearchArtist(artist: String): ArtistSearch = ArtistSearch(
        total = artist.length,
        artists = artists.map { ArtistSearch.Artist(id = ""+it.id, name = it.name) }
    )

}


