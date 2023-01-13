package com.architectcoders.musictoday.ui.main

import app.cash.turbine.test
import com.architectcoders.appTestShared.buildArtistRepository
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.main.MainViewModel.*
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MainIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(testDispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    private fun buildViewModel(localData: List<Artist> = emptyList(), remoteData: List<Artist> = emptyList()): MainViewModel{
        val artistRepository = buildArtistRepository(localData, remoteData)
        val getPopularArtistUseCase = GetPopularArtistUseCase(artistRepository)
        val requestArtistsUseCase = RequestArtistsUseCase(artistRepository)
        return MainViewModel(getPopularArtistUseCase, requestArtistsUseCase)
    }

    @Test
    fun `data is loaded from server when local data is empty`() = runTest {
        val remoteData = listOf(sampleArtist.copy(id = 1))
        val vm = buildViewModel(remoteData = remoteData)

        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            assertEquals(UiState(loading = false, artists = remoteData), awaitItem())
            cancel()
        }
    }

    @Test
    fun `data is loaded from local data when available`() = runTest{
        val localData = listOf(sampleArtist.copy(id = 1), sampleArtist.copy(id = 2))
        val vm = buildViewModel(localData, remoteData = emptyList())

        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = localData), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            cancel()
        }
    }

}



