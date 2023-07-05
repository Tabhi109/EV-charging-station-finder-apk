package com.plugmap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_activity.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        startButton.setOnClickListener {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
