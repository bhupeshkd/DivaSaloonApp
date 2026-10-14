package com.example.mytestapplication.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.mytestapplication.data.api.dto.CreateAddressRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import kotlin.coroutines.resume

data class ResolvedLocation(
    val homeLabel: String,
    val title: String,
    val subtitle: String,
    val line1: String,
    val city: String,
    val state: String?,
    val postalCode: String?,
    val country: String,
    val lat: Double,
    val lng: Double,
) {
    fun toRequest(isDefault: Boolean): CreateAddressRequest = CreateAddressRequest(
        label = "Current",
        line1 = line1,
        city = city,
        state = state,
        postalCode = postalCode,
        country = country,
        lat = lat,
        lng = lng,
        isDefault = isDefault,
    )
}

class DeviceLocationReader(private val context: Context) {

    fun hasPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    suspend fun resolve(): ResolvedLocation? {
        val location = readLocation() ?: return null
        return withContext(Dispatchers.IO) {
            val address = reverseGeocode(location.latitude, location.longitude)
            val line1 = address?.thoroughfare?.let { street ->
                listOfNotNull(address.subThoroughfare, street).joinToString(" ")
            } ?: address?.featureName ?: "Current location"
            val city = address?.locality
                ?: address?.subAdminArea
                ?: address?.adminArea
                ?: "Unknown"
            val state = address?.adminArea
            val postal = address?.postalCode
            val country = address?.countryCode?.takeIf { it.length == 2 } ?: "IN"
            val homeLabel = listOfNotNull(address?.locality ?: address?.subAdminArea, state)
                .filter { it.isNotBlank() }
                .distinct()
                .joinToString(", ")
                .ifBlank { line1 }
            val subtitle = address?.getAddressLine(0) ?: "$city${state?.let { ", $it" }.orEmpty()}"
            ResolvedLocation(
                homeLabel = homeLabel,
                title = line1.substringBefore(","),
                subtitle = subtitle,
                line1 = line1,
                city = city,
                state = state,
                postalCode = postal,
                country = country,
                lat = location.latitude,
                lng = location.longitude,
            )
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun readLocation(): Location? {
        if (!hasPermission()) return null
        val manager = context.getSystemService(LocationManager::class.java) ?: return null
        val last = newestLastKnown(manager)
        if (last != null && System.currentTimeMillis() - last.time < FRESH_MS) return last
        val updated = withTimeoutOrNull(LOCATION_TIMEOUT_MS) { requestSingleUpdate(manager) }
        return updated ?: last
    }

    @SuppressLint("MissingPermission")
    private fun newestLastKnown(manager: LocationManager): Location? {
        return listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
            .filter { runCatching { manager.isProviderEnabled(it) }.getOrDefault(false) }
            .mapNotNull { provider ->
                runCatching { manager.getLastKnownLocation(provider) }.getOrNull()
            }
            .maxByOrNull { it.time }
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestSingleUpdate(manager: LocationManager): Location? {
        val provider = when {
            runCatching { manager.isProviderEnabled(LocationManager.GPS_PROVIDER) }.getOrDefault(false) ->
                LocationManager.GPS_PROVIDER
            runCatching { manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) }.getOrDefault(false) ->
                LocationManager.NETWORK_PROVIDER
            else -> return null
        }
        return suspendCancellableCoroutine { cont ->
            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    manager.removeUpdates(this)
                    if (cont.isActive) cont.resume(location)
                }

                @Deprecated("Deprecated in Java")
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit

                override fun onProviderEnabled(provider: String) = Unit

                override fun onProviderDisabled(provider: String) = Unit
            }
            try {
                manager.requestLocationUpdates(provider, 0L, 0f, listener, Looper.getMainLooper())
            } catch (_: SecurityException) {
                if (cont.isActive) cont.resume(null)
                return@suspendCancellableCoroutine
            }
            cont.invokeOnCancellation { manager.removeUpdates(listener) }
        }
    }

    private suspend fun reverseGeocode(lat: Double, lng: Double): android.location.Address? {
        if (!Geocoder.isPresent()) return null
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { cont ->
                    geocoder.getFromLocation(
                        lat,
                        lng,
                        1,
                        object : Geocoder.GeocodeListener {
                            override fun onGeocode(addresses: MutableList<android.location.Address>) {
                                if (cont.isActive) cont.resume(addresses.firstOrNull())
                            }

                            override fun onError(errorMessage: String?) {
                                if (cont.isActive) cont.resume(null)
                            }
                        },
                    )
                }
            } else {
                @Suppress("DEPRECATION")
                geocoder.getFromLocation(lat, lng, 1)?.firstOrNull()
            }
        } catch (_: Exception) {
            null
        }
    }

    companion object {
        private const val FRESH_MS = 2 * 60 * 1000L
        private const val LOCATION_TIMEOUT_MS = 12_000L
    }
}
