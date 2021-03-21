package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        showProgressDialog()
        try {
            loginWithEmailAndPassword(loginEmail.text.toString(), loginPassword.text.toString()) {
                closeProgressDialog()
                goToSessionListActivity()
            }
        } catch (err: Error) {
            closeProgressDialog()
        }
    }


    private fun goToSessionListActivity() {
        val intent = Intent(this@LoginActivity, SessionsListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
