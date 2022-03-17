package com.architectcoders.musictoday.ui.main

import androidx.lifecycle.*
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.log
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val popularArtists: PopularArtists? = null,
        val navigateTo: PopularArtists.Artist? = null
    )

    private val _state = MutableLiveData(UiState())
    val state:LiveData<UiState> get() {
        if(_state.value?.popularArtists == null) refresh()
        return _state
    }

    private fun refresh(){
        viewModelScope.launch{
            _state.value = UiState(loading = true)
            _state.value = UiState(popularArtists = MusicService.service.getPopularArtists())
//            log("refresh ${state.value?.popularArtists?.artists}")
        }
    }

    fun onArtistClicked(artist: PopularArtists.Artist){
        _state.value = _state.value?.copy(navigateTo = artist)
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }

}
