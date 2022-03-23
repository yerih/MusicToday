package com.architectcoders.musictoday.ui.main

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.musictoday.model.PopularArtists
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
) = MainState(scope, navController, locationPermissionRequester)

class MainState(
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequest: PermissionRequester
) {


    fun onArtistClicked(artist: PopularArtists.Artist){
        val action = MainFragmentDirections.actionMainToDetail(artist)
        navController.navigate(action)
    }

    fun requestLocationPermission(onRequestDone: (Boolean) -> Unit){
        scope.launch { onRequestDone(locationPermissionRequest.request()) }
    }
}