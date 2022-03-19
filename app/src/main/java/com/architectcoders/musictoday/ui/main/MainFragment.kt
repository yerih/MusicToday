package com.architectcoders.musictoday.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentMainBinding
import com.architectcoders.musictoday.model.PopularArtists
import com.architectcoders.musictoday.model.log
import com.architectcoders.musictoday.ui.detail.DetailFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels{MainViewModelFactory()}
    private val adapter = ArtistAdapter{ viewModel.onArtistClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{binding.updateUI(it)}
            }
        }
    }

    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        //TODO: loader true
        state.popularArtists?.let { it ->
            adapter.artists = it.artists
            recycler.adapter = adapter
        }
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(artist: PopularArtists.Artist){
        val action = MainFragmentDirections.actionMainToDetail(artist)
        findNavController().navigate(action)
    }
}