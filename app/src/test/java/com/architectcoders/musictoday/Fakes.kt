package com.architectcoders.musictoday

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

val defaultFakeArtists = listOf(
    sampleArtist.copy(1),
    sampleArtist.copy(2),
    sampleArtist.copy(3),
    sampleArtist.copy(4),
)

class FakeLocalDataSource : ArtistLocalDataSource {

    val fakeArtists = MutableStateFlow<List<Artist>>(emptyList())

    private lateinit var findArtistFlow: MutableStateFlow<Artist>

    override val artists = fakeArtists

    override suspend fun isEmpty(): Boolean = artists.value.isEmpty()

    override fun findById(id: Int): Flow<Artist> {
        findArtistFlow = MutableStateFlow(fakeArtists.value.first{ it.id == id})
        return findArtistFlow
    }//.also { findArtistFlow = it }

    override suspend fun save(artists: List<Artist>): Error? {
        fakeArtists.value = artists
        if(::findArtistFlow.isInitialized)
            artists.firstOrNull{ it.id == findArtistFlow.value.id}?.let { findArtistFlow.value = it }
        return null
    }

}

class FakeRemoteDataSource : ArtistRemoteDataSource{

    var artists = defaultFakeArtists

    override suspend fun getPopularArtists(): Either<Error, List<Artist>> = artists.right()

    override suspend fun getArtistInfo(artist: Artist): Either<Error, Artist> = artists.first { it.id == artist.id }.right()

}

fun buildArtistRepository(localData: List<Artist>, remoteDataSource: List<Artist>): ArtistRepository = ArtistRepository(
    FakeLocalDataSource().apply { fakeArtists.value = localData },
    FakeRemoteDataSource().apply { artists = remoteDataSource }
)


