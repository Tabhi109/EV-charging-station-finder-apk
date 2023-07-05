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

        chargingStations.add(ChargingStation("Kengeri", 12.9081, 77.4850))
        chargingStations.add(ChargingStation("RR Nagar", 12.9278, 77.5156))
        chargingStations.add(ChargingStation("Srinivasapura", 13.0452,  77.8743))
        chargingStations.add(ChargingStation("Banashankari", 12.9255, 77.5468))
        chargingStations.add(ChargingStation("JP Nagar", 12.9077, 77.5785))
        chargingStations.add(ChargingStation("Jayanagar", 12.9289, 77.5822))
        chargingStations.add(ChargingStation("Koramangala", 12.9352, 77.6259))
        chargingStations.add(ChargingStation("HSR Layout", 12.9116, 77.6389))


        return chargingStations
    }

    private fun displayChargingStations(chargingStations: List<ChargingStation>) {
        val stationNames = chargingStations.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stationNames)
        chargingStationList.adapter = adapter
    }

    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=EV Charging Station")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mapIntent)
    }

}

data class ChargingStation(val name: String, val latitude: Double, val longitude: Double)
