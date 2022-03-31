package com.architectcoders.musictoday.ui.detail

import androidx.lifecycle.*
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.data.Error
import com.architectcoders.musictoday.domain.FavoriteToggleUseCase
import com.architectcoders.musictoday.domain.FindArtistByIdUseCase
import com.architectcoders.musictoday.domain.GetArtistInfoUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val artistId: Int,
    findArtistByIdUseCase: FindArtistByIdUseCase,
    getArtistInfoUseCase: GetArtistInfoUseCase,
    private val favoriteToggleUseCase: FavoriteToggleUseCase
) : ViewModel() {

    data class UiState(val artist: ArtistEntity? = null, val error: Error? = null)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findArtistByIdUseCase(artistId).collect { artist ->
                    val error = getArtistInfoUseCase(artist)
                    error?.let { _state.update { it.copy(error = error) } }
                        ?: findArtistByIdUseCase(artistId).collect { artistUpdate ->
                            _state.update { it.copy(artist = artistUpdate) }
                        }
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.artist?.let { favoriteToggleUseCase(it) }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val id: Int,
    private val findArtistByIdUseCase: FindArtistByIdUseCase,
    private val getArtistInfoUseCase: GetArtistInfoUseCase,
    private val favoriteToggleUseCase: FavoriteToggleUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(
            id,
            findArtistByIdUseCase, getArtistInfoUseCase, favoriteToggleUseCase) as T
    }
}


