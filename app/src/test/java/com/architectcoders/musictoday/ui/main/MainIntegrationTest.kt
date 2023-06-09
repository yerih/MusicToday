package com.architectcoders.musictoday.ui.main

import app.cash.turbine.test
import com.architectcoders.appTestShared.buildArtistDB
import com.architectcoders.appTestShared.buildArtistRepository
import com.architectcoders.musictoday.data.database.toDomainModel
import com.architectcoders.musictoday.data.database.ArtistEntity as ArtistDB
import com.architectcoders.musictoday.ui.compose.home.HomeViewModel
import com.architectcoders.musictoday.ui.compose.home.HomeViewModel.*
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

    private fun buildViewModel(localData: List<ArtistDB> = emptyList(), remoteData: List<ArtistDB> = emptyList(), country: String? = null): HomeViewModel {
        val artistRepository = buildArtistRepository(localData, remoteData, country)
        val getPopularArtistUseCase = GetPopularArtistUseCase(artistRepository)
        val requestArtistsUseCase = RequestArtistsUseCase(artistRepository)
        return HomeViewModel(getPopularArtistUseCase, requestArtistsUseCase)
    }

    @Test
    fun `data is loaded from server when local data is empty and gps is turn off or fail getting country`() = runTest {
        val remoteData = buildArtistDB(1,2,3)
        val vm = buildViewModel(remoteData = remoteData, country = null)

        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            val artists = awaitItem().artists!!
            assertEquals(artists[0].name, "name 1")
            assertEquals(artists[1].name, "name 2")
            assertEquals(artists[2].name, "name 3")
            cancel()
        }
    }
    @Test
    fun `data is loaded from server when local data is empty and gps gets country`() = runTest {
        val remoteData = buildArtistDB(1,2,3)
        val vm = buildViewModel(remoteData = remoteData, country = "US")

        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = emptyList(), loading = false), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            val artists = awaitItem().artists!!
            assertEquals(artists[0].name, "name 1")
            assertEquals(artists[1].name, "name 2")
            assertEquals(artists[2].name, "name 3")
            cancel()
        }
    }

    @Test
    fun `data is loaded from local data when available`() = runTest{
        val localData = buildArtistDB(1,2,3)
        val vm = buildViewModel(localData, remoteData = emptyList())

        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = localData.toDomainModel()), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            cancel()
        }
    }

}



