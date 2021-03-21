package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.adapters.SessionUserAdapter
import fr.thibaultlepez.chill.services.removeUserFromSession
import fr.thibaultlepez.chill.store.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SessionOverviewActivity : BaseActivity() {

    private lateinit var sessionId: String

    private lateinit var overviewSessionName: TextView
    private lateinit var overviewSessionId: TextView
    private lateinit var usersList: RecyclerView
    private lateinit var editButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_overview)


        sessionId = intent.extras?.get("sessionId") as String


        overviewSessionName = findViewById(R.id.users_list_session_name)
        overviewSessionId = findViewById(R.id.users_list_session_id)
        usersList = findViewById(R.id.users_list_list_view)
        editButton = findViewById(R.id.users_list_activity_edit_session_button)


        showProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            State.loadSessionInState(this@SessionOverviewActivity, sessionId)

            withContext(Dispatchers.Main) {
                setSessionName()
                setSessionId()
                displayUsersList()
                // setEditButtonVisibility()

                closeProgressDialog()
            }
        }
    }



    private fun setSessionName() {
        overviewSessionName.text = State.session?.name ?: "Session not found"
    }


    private fun setSessionId() {
        overviewSessionId.text = "Join ID: ${State.session?.id ?: "Session not found"}"
    }


    private fun displayUsersList() {
        usersList.layoutManager = LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        usersList.adapter =  SessionUserAdapter(this, State.session!!.users)
    }


    private fun setEditButtonVisibility() {
        editButton.visibility = if (State.session!!.ownerID == State.user!!.id) View.VISIBLE else View.GONE
    }


    fun goToSessionActivity(view: View) {
        // TODO
    }


    fun editSession(view: View) {
        // TODO
    }


    fun leaveSession(view: View) {
        showProgressDialog()
        CoroutineScope(Dispatchers.IO).launch {
            removeUserFromSession(State.user!!.id, State.session!!.id)

            withContext(Dispatchers.Main) {
                val intent = Intent(this@SessionOverviewActivity, SessionsListActivity::class.java)
                startActivity(intent)

                closeProgressDialog()
                finish()
            }
        }
    }
}