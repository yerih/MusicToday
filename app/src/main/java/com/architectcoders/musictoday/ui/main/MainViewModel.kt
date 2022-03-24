package com.architectcoders.musictoday.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.common.LocationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val locationHelper: LocationHelper) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val popularArtists: PopularArtists? = null,
        val artistsByLocation: ArtistsByLocation.TopArtists? = null,
        val navigateTo: PopularArtists.Artist? = null,
        val requestPermissionLocation: Boolean = true
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady(){
        viewModelScope.launch{
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(artistsByLocation = locationHelper.getCountryByGPS())
            _state.value.artistsByLocation?.artists?.run {
                val artistList = mutableListOf<PopularArtists.Artist>()
                take(10).forEach { artist ->
                    val result = MusicService.service.getSearchArtist(artist = artist.name)
                    val artist2 = PopularArtists.Artist(name = artist.name, picture_medium = result.artists[0].picture_medium)
                    artistList.add(artist2)
                }
                _state.value = _state.value.copy(popularArtists = PopularArtists(artistList, artistList.size))
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    { return MainViewModel(LocationHelper(app)) as T }
}
