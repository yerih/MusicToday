package com.architectcoders.musictoday.ui.detail

import app.cash.turbine.test
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.detail.DetailViewModel.*
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
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
class DetailViewModelTest{

    @Mock
    private lateinit var findArtistByIdUseCase: FindArtistByIdUseCase

    @Mock
    private lateinit var getArtistInfoUseCase: GetArtistInfoUseCase

    @Mock
    private lateinit var favoriteToggleUseCase: FavoriteToggleUseCase

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var vm: DetailViewModel
    private var artist = sampleArtist.copy(id = 2)

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        whenever(findArtistByIdUseCase(2)).thenReturn(flowOf(artist))
        vm = DetailViewModel(2,  findArtistByIdUseCase, getArtistInfoUseCase, favoriteToggleUseCase)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `display is set when artist info is gotten`() = runTest{
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(artist = artist), awaitItem())
        }
    }

    @Test
    fun `check if favorite use case is called`() = runTest{
        vm.onFavoriteClicked()
        runCurrent()

        verify(favoriteToggleUseCase).invoke(artist)
    }
}