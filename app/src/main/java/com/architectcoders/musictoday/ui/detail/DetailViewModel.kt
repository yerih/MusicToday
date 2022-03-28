package com.architectcoders.musictoday.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val artistId: Int,
    private val repository: ArtistRepository
): ViewModel() {

    data class UiState(val artist: ArtistEntity? = null)

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.findById(artistId).collect {
                repository.getArtistInfo(it)
                _state.value = UiState(it)
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.artist?.let { repository.favoriteToggle(it) }
//            repository.findById(artistId).collect {
//                repository.favoriteToggle(it)
//                _state.value = _state.value.copy(artist = it.copy(favorite = true))
//            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val id: Int, private val repository: ArtistRepository) :
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(id, repository) as T
    }
}


