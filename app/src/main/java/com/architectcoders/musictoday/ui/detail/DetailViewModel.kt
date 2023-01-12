package com.architectcoders.musictoday.ui.detail

import androidx.lifecycle.*
import arrow.core.right
import com.architectcoders.musictoday.di.ArtistId
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import com.architectcoders.musictoday.sampleArtist
import com.architectcoders.musictoday.ui.common.log
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @ArtistId artistId: Int,
    findArtistByIdUseCase: FindArtistByIdUseCase,
    getArtistInfoUseCase: GetArtistInfoUseCase,
    private val favoriteToggleUseCase: FavoriteToggleUseCase
) : ViewModel() {


    data class UiState(val artist: Artist? = null, val error: Error? = null)
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findArtistByIdUseCase(artistId).collect { artist ->
                val result = getArtistInfoUseCase(artist)
                result?.let { _state.update { it.copy(error = result) } }
                    ?: findArtistByIdUseCase(artistId).collect { artistUpdate ->
                        _state.update { it.copy(artist = artistUpdate) }
                    }
            }
        }
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        _state.value.artist?.let { artist -> _state.update { it.copy(error = favoriteToggleUseCase(artist)) } }
    }

}



