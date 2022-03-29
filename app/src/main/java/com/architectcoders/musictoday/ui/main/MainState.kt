package com.architectcoders.musictoday.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.musictoday.R
import com.architectcoders.musictoday.database.ArtistEntity
import com.architectcoders.musictoday.model.Error
import com.architectcoders.musictoday.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    scope: CoroutineScope = lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(requireContext(), scope, navController, locationPermissionRequester)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequest: PermissionRequester
) {


    fun onArtistClicked(artist: ArtistEntity){
        val action = MainFragmentDirections.actionMainToDetail(artist.id)
        navController.navigate(action)
    }

    fun requestLocationPermission(onRequestDone: (Boolean) -> Unit){
        scope.launch { onRequestDone(locationPermissionRequest.request()) }
    }

    fun errorToString(error: Error): String = when(error){
        Error.Connectivity ->   context.getString(R.string.connectivity_error)
        is Error.Server ->      context.getString(R.string.server_error)
        is Error.Unknown ->     context.getString(R.string.unknown_error)
    }
}