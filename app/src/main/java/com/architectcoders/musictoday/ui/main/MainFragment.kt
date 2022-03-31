package com.architectcoders.musictoday.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentMainBinding
import com.architectcoders.musictoday.data.app
import com.architectcoders.musictoday.data.launchAndCollect
import com.architectcoders.musictoday.usecases.GetPopularArtistUseCase
import com.architectcoders.musictoday.usecases.RequestArtistsUseCase


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels{
        val repository = ArtistRepository(requireActivity().app)
        MainViewModelFactory(
            GetPopularArtistUseCase(repository),
            RequestArtistsUseCase(repository)
        )
    }
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