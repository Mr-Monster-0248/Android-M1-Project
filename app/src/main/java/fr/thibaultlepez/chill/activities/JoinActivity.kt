package fr.thibaultlepez.chill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.thibaultlepez.chill.R

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    fun joinSession(view: View) {}
    fun goToEditActivity(view: View) {}
}