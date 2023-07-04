package com.plugmap
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.plugmap.databinding.MainActivityBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plugmap.StationListActivity
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationInput = binding.locationInput
        val findButton = binding.findButton
        val locateMeButton = binding.locateMeButton

        findButton.setOnClickListener {
            val location = locationInput.text.toString()
            showFindingMessage()
            openStationListActivity(location)
        }

        locateMeButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val address = getAddress(latitude, longitude)
                        binding.locationInput.setText(address)
                    } ?: run {
                        Toast.makeText(
                            this,
                            "Failed to retrieve location",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("Location", "Failed to retrieve location")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error getting location: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Location", "Error getting location", e)
                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    private fun getAddress(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        var addressText = ""
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                addressText = address.getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressText
    }

    private fun openStationListActivity(location: String) {
        val intent = Intent(this, StationListActivity::class.java)
        intent.putExtra("location", location)
        startActivity(intent)
    }

    private fun showFindingMessage() {
        binding.messageText.text = "Finding nearest EV charging station for you..."
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
