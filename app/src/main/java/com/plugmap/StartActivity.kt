package com.plugmap

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    private val delayMillis = 2500L // 2.5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        Handler().postDelayed({
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}
