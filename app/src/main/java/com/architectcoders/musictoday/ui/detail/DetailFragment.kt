package com.architectcoders.musictoday.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.architectcoders.musictoday.data.ArtistRepository
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.databinding.FragmentDetailBinding
import com.architectcoders.musictoday.data.database.ArtistRoomDataSource
import com.architectcoders.musictoday.data.server.ArtistServerDataSource
import com.architectcoders.musictoday.data.server.errorToString
import com.architectcoders.musictoday.ui.common.LocationHelper
import com.architectcoders.musictoday.ui.common.app
import com.architectcoders.musictoday.ui.common.launchAndCollect
import com.architectcoders.musictoday.usecases.FavoriteToggleUseCase
import com.architectcoders.musictoday.usecases.FindArtistByIdUseCase
import com.architectcoders.musictoday.usecases.GetArtistInfoUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        binding.artistDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.btnFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
        viewLifecycleOwner.launchAndCollect(flowOf(viewModel.state)){ it ->
            binding.artist = it.artist
            binding.error = it.error?.errorToString(requireContext())
        }
    }

}