package com.architectcoders.musictoday.ui.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.architectcoders.musictoday.model.MusicService
import com.architectcoders.musictoday.ui.main.ArtistsByLocation
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }
}

class LocationManager(application: Application) {

    companion object {
        private const val DEFAULT_COUNTRY = "United States"
    }

    private val locationDataSource: LocationDataSource = PlayServicesLocationDataSource(application)
    private val coarsePermissionChecker = PermissionChecker(
        application,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(application)

    suspend fun findMyCountry(): String = findLastLocation().toCountry()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.check()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toCountry(): String {
        val addresses = this?.let { geocoder.getFromLocation(latitude, longitude, 1) }
        return addresses?.firstOrNull()?.countryName ?: DEFAULT_COUNTRY
    }
}


class LocationHelper(private val app: Application){
    suspend fun getCountryByGPS() : ArtistsByLocation.TopArtists{
        val country = LocationManager(app).findMyCountry()
        return MusicService.service.getArtistByLocation(country).topArtists
    }
}

