package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentDetailBinding
import com.architectcoders.musictoday.model.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(safeArgs.artist) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        val detailState = buildDetailState()
        binding.artistDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        viewLifecycleOwner.launchAndCollect(viewModel.state){
            binding.artist = it.artistInfo
            binding.urlImage = safeArgs.artist.picture_medium
        }
        detailState.onArtistInfoRequested(safeArgs.artist){ viewModel.onUiReady(it) }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailViewModel.UiState){
        state.artistInfo?.let {
            artistDetailSummary.text = it.bio.published
            artistDetailInfo.text = it.bio.summary
            artistDetailToolbar.title = it.name
        }
        state.popularArtist?.let { artistDetailImage.loadUrl(it.picture_medium) }
    }

}