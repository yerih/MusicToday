package com.architectcoders.musictoday.ui.compose.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.architectcoders.musictoday.data.server.PopularArtists
import com.architectcoders.musictoday.data.server.toError
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPopularArtistUseCase: GetPopularArtistUseCase,
    private val requestArtistsUseCase: RequestArtistsUseCase
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val artists: List<Artist>? = null,
        val artistsByLocation: ArtistsByLocation.TopArtists? = null,
        val navigateTo: PopularArtists.Artist? = null,
        val requestPermissionLocation: Boolean = true,
        val error: com.architectcoders.musictoday.domain.Error? = null
    )

    var state by mutableStateOf(UiState())
        private set
    
    init {
        viewModelScope.launch {
            getPopularArtistUseCase()
                .catch { cause -> state = state.copy(error = cause.toError()) }
                .collect { state = UiState(artists = it) }
        }
    }

    fun onUiReady(){
        viewModelScope.launch {
            state = UiState(loading = true)
            val error = requestArtistsUseCase()
            state = state.copy(loading = false, error = error)
        }
    }

}

