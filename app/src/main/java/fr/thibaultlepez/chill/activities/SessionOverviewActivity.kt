package fr.thibaultlepez.chill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.store.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionOverviewActivity : AppCompatActivity() {

    private lateinit var sessionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_overview)

        sessionId = intent.extras?.get("sessionId") as String
        State.loadSessionInState(this, sessionId)
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