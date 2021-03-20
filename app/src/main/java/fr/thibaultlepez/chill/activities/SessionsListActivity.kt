package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.models.User
import fr.thibaultlepez.chill.services.getUserFromDb
import fr.thibaultlepez.chill.store.State

class SessionsListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions_list)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) getUserFromDb(user.uid) {
            State.user = User(
                it.id,
                if (user.email != null) user.email else "",
                it.username
            )
        }
        else returnToLoginActivity()
    }

    fun goToJoinActivity(view: View) {
        val intent = Intent(this@SessionsListActivity, JoinActivity::class.java)
        startActivity(intent)
    }


    // TODO: link session to its activity
}