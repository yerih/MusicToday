package com.architectcoders.musictoday.ui.main

import androidx.lifecycle.*
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.data.*
import com.architectcoders.musictoday.domain.GetPopularArtistUseCase
import com.architectcoders.musictoday.domain.RequestArtistsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MainViewModel(
    getPopularArtistUseCase: GetPopularArtistUseCase,
    private val requestArtistsUseCase: RequestArtistsUseCase
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val artists: List<ArtistEntity>? = null,
        val artistsByLocation: ArtistsByLocation.TopArtists? = null,
        val navigateTo: PopularArtists.Artist? = null,
        val requestPermissionLocation: Boolean = true,
        val error: Error? = null
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
            _state.update { it.copy(error = error) }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getPopularArtistUseCase: GetPopularArtistUseCase,
    private val requestArtistsUseCase: RequestArtistsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getPopularArtistUseCase, requestArtistsUseCase) as T
    }
}
