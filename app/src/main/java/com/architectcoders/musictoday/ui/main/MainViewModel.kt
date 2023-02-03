package com.architectcoders.musictoday.ui.main

import androidx.lifecycle.*
import com.architectcoders.musictoday.data.*
import com.architectcoders.musictoday.data.server.PopularArtists
import com.architectcoders.musictoday.data.server.toError
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularArtistUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { _state.value = UiState(artists = it) }
        }
    }

    fun onUiReady(){
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val error = requestArtistsUseCase()
            _state.update { it.copy(loading = false, error = error) }
        }
    }

}

