package com.architectcoders.musictoday.ui.main

import app.cash.turbine.test
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.main.MainViewModel.*
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    lateinit var requestArtistUseCase: RequestArtistsUseCase

    @Mock
    lateinit var getPopularArtistUseCase: GetPopularArtistUseCase

    private lateinit var vm: MainViewModel
    private var artists = listOf(sampleArtist.copy(id = 2))
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        whenever(getPopularArtistUseCase()).thenReturn(flowOf(artists))
        vm = MainViewModel(getPopularArtistUseCase, requestArtistUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `State is updated with cache`() = runTest {
        vm.state.test{
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = artists), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when request is executing and hidden when request is finished`() = runTest {
        vm.onUiReady()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artists = artists, loading = false), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Check if artist request is invoked`() = runTest {
        vm.onUiReady()
        runCurrent()
        verify(requestArtistUseCase).invoke()
    }
}