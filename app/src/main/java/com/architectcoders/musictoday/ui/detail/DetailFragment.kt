package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.architectcoders.musictoday.ArtistRepository
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentDetailBinding
import com.architectcoders.musictoday.data.*
import com.architectcoders.musictoday.domain.FavoriteToggleUseCase
import com.architectcoders.musictoday.domain.FindArtistByIdUseCase
import com.architectcoders.musictoday.domain.GetArtistInfoUseCase


class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels {
        val repository = ArtistRepository(requireActivity().app)
        DetailViewModelFactory(safeArgs.artistId,
            FindArtistByIdUseCase(repository),
            GetArtistInfoUseCase(repository),
            FavoriteToggleUseCase(repository)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        binding.artistDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.btnFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
        viewLifecycleOwner.launchAndCollect(viewModel.state){ it ->
            binding.artist = it.artist
            binding.error = it.error?.errorToString(requireContext())
        }
    }

}