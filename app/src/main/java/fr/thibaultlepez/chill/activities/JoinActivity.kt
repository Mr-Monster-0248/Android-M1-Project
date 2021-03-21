package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinActivity : BaseActivity() {
    private lateinit var sessionIdTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        sessionIdTextView = findViewById(R.id.join_activity_session_id)
    }


    fun joinSession(view: View) {
        showProgressDialog()

        val functions = FirebaseFunctions.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val sessionId = sessionIdTextView.text.toString()

        val data = hashMapOf(
            "sessionId" to sessionId,
            "userId" to userId
        )

        CoroutineScope(Dispatchers.IO).launch {
            functions.getHttpsCallable(Constants.ADD_USER_TO_SESSION_FUNC)
                .call(data)
                .addOnCompleteListener {
                    closeProgressDialog()
                }
                .addOnSuccessListener {
                    showSnackBar("new session added")
                    startActivity(Intent(this@JoinActivity, SessionsListActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    showSnackBar("fail to join session", true)
                }
        }
    }


    fun goToEditActivity(view: View) {
        val intent = Intent(this@JoinActivity, EditActivity::class.java)
        startActivity(intent)
    }
}