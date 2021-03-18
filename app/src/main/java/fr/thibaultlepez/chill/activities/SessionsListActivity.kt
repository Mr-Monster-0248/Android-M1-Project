package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.thibaultlepez.chill.R

class SessionsListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions_list)
    }

    fun goToJoinActivity(view: View) {
        val intent = Intent(this@SessionsListActivity, JoinActivity::class.java)
        startActivity(intent)
    }


    // TODO: link session to its activity
}