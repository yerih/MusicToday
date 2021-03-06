package com.architectcoders.musictoday.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentMainBinding
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.common.app
import com.architectcoders.musictoday.ui.common.launchAndCollect
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = ArtistAdapter{ mainState.onArtistClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply { recycler.adapter = adapter }
        mainState = buildMainState()
        viewLifecycleOwner.launchAndCollect(viewModel.state){
            binding.loading = it.loading
            binding.artists = it.artists
            binding.error = it.error?.let(mainState::errorToString)
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
    }

}