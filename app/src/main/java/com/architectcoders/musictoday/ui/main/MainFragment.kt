package com.architectcoders.musictoday.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentMainBinding
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.launchAndCollect
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.detail.DetailFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels{MainViewModelFactory(requireActivity().application)}
    private val adapter = ArtistAdapter{ mainState.onArtistClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply { recycler.adapter = adapter }
        mainState = buildMainState()
        viewLifecycleOwner.launchAndCollect(viewModel.state){
            binding.loading = it.loading
            binding.artists = it.popularArtists?.artists
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
    }

}