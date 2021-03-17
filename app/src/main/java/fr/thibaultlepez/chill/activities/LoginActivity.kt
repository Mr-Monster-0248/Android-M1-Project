package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import fr.thibaultlepez.chill.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun goToSessionListActivity(view: View) {
        val intent = Intent(this@LoginActivity, SessionsListActivity::class.java)
        startActivity(intent)
    }

    fun goToRegisterActivity(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}
