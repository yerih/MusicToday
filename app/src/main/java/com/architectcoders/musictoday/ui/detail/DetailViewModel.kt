package com.architectcoders.musictoday.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.architectcoders.musictoday.di.Id
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.domain.Error
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Id artistId: Int,
    findArtistByIdUseCase: FindArtistByIdUseCase,
    getArtistInfoUseCase: GetArtistInfoUseCase,
    private val favoriteToggleUseCase: FavoriteToggleUseCase
) : ViewModel() {

    data class UiState(val artist: Artist? = null, val error: Error? = null)
    var state by mutableStateOf(UiState())
    private set

    init {
        viewModelScope.launch {
            findArtistByIdUseCase(artistId).collect { artist ->
                val result = getArtistInfoUseCase(artist)
                result?.let { state = state.copy(error = result) }
                ?: findArtistByIdUseCase(artistId).collect { artistUpdate -> state = state.copy(artist = artistUpdate) }
            }
        }
    }

    fun onFavoriteClicked() = viewModelScope.launch {
        this@DetailViewModel.state.artist?.let { artist -> this@DetailViewModel.state = state.copy(error = favoriteToggleUseCase(artist)) }
    }

}



