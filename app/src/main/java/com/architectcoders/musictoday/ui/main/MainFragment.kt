package com.architectcoders.musictoday.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentMainBinding
import com.architectcoders.musictoday.ui.common.launchAndCollect
import com.architectcoders.musictoday.ui.compose.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ArtistAdapter{ mainState.onArtistClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply { recycler.adapter = adapter }
        mainState = buildMainState()
        viewLifecycleOwner.launchAndCollect(flowOf(viewModel.state)){
            binding.loading = it.loading
            binding.artists = it.artists
            binding.error = it.error?.let(mainState::errorToString)
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
    }

}