package com.architectcoders.musictoday.ui.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


interface LocationDataSource {
    suspend fun findLastLocation(): Location?
    suspend fun getCountryByGPS(): String?
}

class PlayServicesLocationDataSource(app: Application) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine {
                continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }

    override suspend fun getCountryByGPS(): String? = null

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

    suspend fun findMyCountry(): String? = findLastLocation().toCountry()

    suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.check()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toCountry(): String? {
        val addresses = this?.let { geocoder.getFromLocation(latitude, longitude, 1) }
        return addresses?.firstOrNull()?.countryName
    }
}


class LocationHelper @Inject constructor(private val app: Application) : LocationDataSource{
    override suspend fun getCountryByGPS(): String? = LocationManager(app).findMyCountry()
    override suspend fun findLastLocation(): Location? = LocationManager(app).findLastLocation()
}

