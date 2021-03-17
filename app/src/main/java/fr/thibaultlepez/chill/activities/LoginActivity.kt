package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.thibaultlepez.chill.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginButton = findViewById<Button>(R.id.login_btn);
        loginButton?.setOnClickListener() {
            // TODO: HANDLE AUTH
            startActivity(Intent(this@LoginActivity, SessionsListActivity::class.java))
            finish()
        }
    }
}