package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import fr.thibaultlepez.chill.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // TODO: check auth and redirect accordingly
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            },
            2500
        )
    }
}