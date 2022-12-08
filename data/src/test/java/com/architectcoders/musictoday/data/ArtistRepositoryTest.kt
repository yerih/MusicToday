package com.architectcoders.musictoday.data

import com.architectcoders.musictoday.data.datasource.ArtistLocalDataSource
import com.architectcoders.musictoday.data.datasource.ArtistRemoteDataSource
import com.architectcoders.musictoday.domain.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


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
}

private val sampleArtist = Artist(
    id = 0,
    name = "Farruko",
    biography = "is a music artist",
    publishingDate = "2022-10-10",
    imageUrl = "https://e-cdns-images.dzcdn.net/images/artist/61cb20fc86e37eac43834dc6e7484e1b/500x500-000000-80-0-0.jpg",
)

