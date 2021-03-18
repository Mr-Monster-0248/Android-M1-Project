package fr.thibaultlepez.chill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.services.loginWithEmailAndPassword

class LoginActivity : BaseActivity() {
    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
    }

    fun goToRegisterActivity(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun loginUser(view: View) {
        try {
            loginWithEmailAndPassword(loginEmail.text.toString(), loginPassword.text.toString()) {
                goToSessionListActivity()
            }
        } catch (err: Error) {
            Log.e("CHILL", "Error while logging user")
        }
    }


    private fun goToSessionListActivity() {
        val intent = Intent(this@LoginActivity, SessionsListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
