package com.architectcoders.musictoday.data

import arrow.core.right
import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*


@RunWith(MockitoJUnitRunner::class)
class ArtistRepositoryTest{

    @Mock
    lateinit var localDataSource: ArtistLocalDataSource

    @Mock
    lateinit var remoteDataSource: ArtistRemoteDataSource

    @Mock
    lateinit var artistRepository: ArtistRepository

    private val localArtist = flowOf(listOf(sampleArtist.copy(id = 1), sampleArtist.copy(id = 2)))

    @Before
    fun setUp(){
        whenever(localDataSource.artists).thenReturn(localArtist)
        artistRepository = ArtistRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Get artist list from local data source if are available`() = runBlocking{
        //GIVEN

        //WHEN
        val result = artistRepository.artists

        Assert.assertEquals(localArtist, result)
    }

    @Test
    fun `Return null when localDataSource is empty`(): Unit = runBlocking{
        //GIVEN
        whenever(localDataSource.isEmpty()).thenReturn(false)

        //WHEN
        val result = artistRepository.requestArtists()

        //ASSERT
        Assert.assertNull(result)
    }

    @Test
    fun `Change favorite field on unfavorite artist`(): Unit = runBlocking {
        val artist = sampleArtist.copy(favorite = false)
        artistRepository.favoriteToggle(artist)
        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Change favorite field on favorite artist`(): Unit = runBlocking {
        val artist = sampleArtist.copy(favorite = true)
        artistRepository.favoriteToggle(artist)
        verify(localDataSource).save(argThat { !get(0).favorite })
    }

    @Test
    fun `favorite field is updated in local data source`(): Unit = runBlocking {
        val artist = sampleArtist.copy(id = 555)
        artistRepository.favoriteToggle(artist)
        verify(localDataSource).save(argThat { get(0).id == 555 })
    }

    @Test
    fun `Find artist by Id and return it`(): Unit = runBlocking {
        val artist = flowOf(sampleArtist.copy(id = 555))
        whenever(localDataSource.findById(555)).thenReturn(artist)
        val result = artistRepository.findById(555)
        Assert.assertEquals(artist, result)
    }

    @Test
    fun `Artists are saved on local data source when list is empty`(): Unit = runBlocking {
        val netArtists = listOf(sampleArtist.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(remoteDataSource.getPopularArtists()).thenReturn(netArtists.right())

        artistRepository.requestArtists()
        verify(localDataSource).save(netArtists)
    }

//    @Test
//    fun `Get artist top list when location is not available`(): Unit = runBlocking{
//        val netArtist = listOf(sampleArtist.copy(123))
//        val remoteDataSource: ArtistRemoteDataSource = mock()
//        whenever(localDataSource.isEmpty()).thenReturn(true)
//        whenever(remoteDataSource.getPopularArtists()).thenReturn(netArtists.right())
//    }
}

private val sampleArtist = Artist(
    id = 0,
    name = "Farruko",
    biography = "is a music artist",
    publishingDate = "2022-10-10",
    imageUrl = "https://e-cdns-images.dzcdn.net/images/artist/61cb20fc86e37eac43834dc6e7484e1b/500x500-000000-80-0-0.jpg",
)


