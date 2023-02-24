package com.architectcoders.musictoday.ui.detail

import app.cash.turbine.test
import com.architectcoders.appTestShared.buildArtistDB
import com.architectcoders.appTestShared.buildArtistRepository
import com.architectcoders.musictoday.data.database.ArtistEntity as ArtistDB
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.detail.DetailViewModel.*
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun tearDown() = Dispatchers.resetMain()

    private fun buildViewModel(artistId: Int, localData: List<ArtistDB> = emptyList(), remoteData: List<ArtistDB> = emptyList()): DetailViewModel{
        val artistRepository      = buildArtistRepository(localData, remoteData)
        val findArtistsUseCase    = FindArtistByIdUseCase(artistRepository)
        val getArtistByIdUseCase  = GetArtistInfoUseCase(artistRepository)
        val favoriteToggleUseCase = FavoriteToggleUseCase(artistRepository)
        return DetailViewModel(artistId, findArtistsUseCase, getArtistByIdUseCase, favoriteToggleUseCase)
    }


    @Test
    fun `view is updated with artist info on start`() = runTest{
        val vm = buildViewModel(1, localData = buildArtistDB(1, 2, 3))
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            val artist = awaitItem().artist!!
            assertEquals(artist.id, 1)
            cancel()
        }
    }

    @Test
    fun `favorite field is updated in local datasource`() = runTest{
        val vm = buildViewModel(1, localData = buildArtistDB(1,2,3))
        vm.onFavoriteClicked()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(sampleArtist.copy(1, favorite = false)), awaitItem())
            assertEquals(UiState(sampleArtist.copy(1, favorite = true)), awaitItem())
            cancel()
        }
    }

}

