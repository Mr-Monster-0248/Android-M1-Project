package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.store.State
import kotlinx.coroutines.*

class SessionsListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions_list)

        Log.i("CHILL", "Il se passe qqchose")
        Log.i("CHILL", State.toString())

        val user = FirebaseAuth.getInstance().currentUser

        CoroutineScope(Dispatchers.IO).launch {
            State.initState(user!!)

            withContext(Dispatchers.Main) {
                // TODO: update UI
            }

            Log.i("CHILL", State.toString())
        }
    }

    fun goToJoinActivity(view: View) {
        val intent = Intent(this@SessionsListActivity, JoinActivity::class.java)
        startActivity(intent)
    }


    // TODO: link session to its activity
}