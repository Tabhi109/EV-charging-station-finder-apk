package com.plugmap

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.*

class StationListActivity : AppCompatActivity() {

    private lateinit var location: String
    private lateinit var chargingStationList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_list)

        chargingStationList = findViewById(R.id.chargingStationList)

        // Get the location from the intent extras
        location = intent.getStringExtra("location") ?: ""

        // Fetch the nearest charging stations based on the location
        val chargingStations = fetchChargingStations(location)

        // Display the charging stations as a list
        displayChargingStations(chargingStations)

        // Set click listener for the list items
        chargingStationList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedStation = chargingStations[position]
            openGoogleMaps(selectedStation.latitude, selectedStation.longitude)
        }
    }

    private fun fetchChargingStations(location: String): List<ChargingStation> {
        // TODO: Implement logic to fetch nearest charging stations based on the location
        // You can use Geocoder or any other APIs/services to fetch the charging stations data
        // For demonstration purposes, let's assume we have a list of ChargingStation objects

        val chargingStations = mutableListOf<ChargingStation>()
        // Example charging stations
        chargingStations.add(ChargingStation("Charging Station 1", 28.7128, 77.5087125))
        chargingStations.add(ChargingStation("Charging Station 2", 34.0522, -118.2437))
        chargingStations.add(ChargingStation("Charging Station 3", 51.5074, -0.1278))

        return chargingStations
    }

    private fun displayChargingStations(chargingStations: List<ChargingStation>) {
        val stationNames = chargingStations.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stationNames)
        chargingStationList.adapter = adapter
    }

    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=EV Charging Station near me")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mapIntent)
    }

}

data class ChargingStation(val name: String, val latitude: Double, val longitude: Double)
