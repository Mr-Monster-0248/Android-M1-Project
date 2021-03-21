package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.adapters.SessionListAdapter
import fr.thibaultlepez.chill.store.State
import kotlinx.coroutines.*

class SessionsListActivity : BaseActivity() {
    lateinit var sessionList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions_list)

        sessionList = findViewById(R.id.sessions_list_list_view)

        val user = FirebaseAuth.getInstance().currentUser

        CoroutineScope(Dispatchers.IO).launch {
            State.initState(user!!)

            withContext(Dispatchers.Main) {
                displayList()
            }

            Log.i("CHILL", State.toString())
        }
    }

    private fun displayList() {
        sessionList.layoutManager = LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        sessionList.adapter =  SessionListAdapter(this, State.sessions)

    }

    fun goToJoinActivity(view: View) {
        val intent = Intent(this@SessionsListActivity, JoinActivity::class.java)
        startActivity(intent)
    }

}