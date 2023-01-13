package com.architectcoders.musictoday.ui.detail

import app.cash.turbine.test
import com.architectcoders.appTestShared.buildArtistRepository
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.detail.DetailViewModel.*
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
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

    private fun buildViewModel(artistId: Int, localData: List<Artist> = emptyList(), remoteData: List<Artist> = emptyList()): DetailViewModel{
        val artistRepository      = buildArtistRepository(localData, remoteData)
        val findArtistsUseCase    = FindArtistByIdUseCase(artistRepository)
        val getArtistByIdUseCase  = GetArtistInfoUseCase(artistRepository)
        val favoriteToggleUseCase = FavoriteToggleUseCase(artistRepository)
        return DetailViewModel(artistId, findArtistsUseCase, getArtistByIdUseCase, favoriteToggleUseCase)
    }


    @Test
    fun `view is updated with artist info on start`() = runTest{
        val artists = listOf(sampleArtist.copy(1), sampleArtist.copy(2))
        val vm = buildViewModel(1, artists, artists)
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artist = sampleArtist.copy(1)), awaitItem())
            cancel()
        }
    }

    @Test
    fun `favorite field is updated in local datasource`() = runTest{
        val artists = listOf(sampleArtist.copy(1), sampleArtist.copy(2))
        val vm = buildViewModel(1, localData = artists, remoteData = artists)
        vm.onFavoriteClicked()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(sampleArtist.copy(1, favorite = false)), awaitItem())
            assertEquals(UiState(sampleArtist.copy(1, favorite = true)), awaitItem())
            cancel()
        }
    }

}

