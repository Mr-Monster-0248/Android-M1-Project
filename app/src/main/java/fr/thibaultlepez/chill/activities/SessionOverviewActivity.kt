package fr.thibaultlepez.chill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.thibaultlepez.chill.R

class SessionOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_overview)
    }


    // TODO: conditionally display EDIT button (only for session owner)


    fun goToSessionActivity(view: View) {
        // TODO
    }


    fun editSession(view: View) {
        // TODO
    }


    fun leaveSession(view: View) {
        // TODO
    }
}