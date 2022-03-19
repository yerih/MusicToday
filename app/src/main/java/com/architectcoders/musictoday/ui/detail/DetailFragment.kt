package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentDetailBinding
import com.architectcoders.musictoday.model.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment: Fragment(R.layout.fragment_detail) {
    companion object{
        const val ARTIST = "DetailActivity:artist"
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(arguments?.getParcelable<PopularArtists.Artist>(ARTIST)!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{binding.updateUI(it)}
            }
        }
    }

    private fun FragmentDetailBinding.updateUI(state: DetailViewModel.UiState){
        state.artistInfo?.let {
            artistDetailToolbar.title = it.name
            artistDetailSummary.text = it.bio.published
            artistDetailInfo.text = it.bio.summary
        }
        state.popularArtist?.let { artistDetailImage.loadUrl(it.picture_medium) }
    }

}