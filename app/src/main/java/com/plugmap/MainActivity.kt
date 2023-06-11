package com.plugmap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.plugmap.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationInput = binding.locationInput
        val findButton = binding.findButton

        findButton.setOnClickListener {
            val location = locationInput.text.toString()
            showFindingMessage()
            openGoogleMaps(this, location)
        }
    }

    private fun openGoogleMaps(context: Context, location: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=ev+charging+stations+near+$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    private fun showFindingMessage() {
        binding.messageText.text = "Finding nearest EV charging station for you..."
    }
}
