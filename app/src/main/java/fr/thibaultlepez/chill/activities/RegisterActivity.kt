package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.services.registerWithEmailAndPassword

class RegisterActivity : BaseActivity() {
    private lateinit var registerEmail: TextInputEditText
    private lateinit var registerPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
    }

    fun registerUser(view: View) {
        showProgressDialog()
        if (validateRegister()) {
            try {
                registerWithEmailAndPassword(registerEmail.text.toString(), registerPassword.text.toString()) { user ->
                    closeProgressDialog()
                    showSnackBar("Register success")
                    val intent = Intent(this@RegisterActivity, SessionsListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } catch (err: Error) {
                closeProgressDialog()
            }
        } else {
            closeProgressDialog()
        }

    }

    private fun validateRegister(): Boolean {
        return when {
            TextUtils.isEmpty(registerEmail.text.toString().trim()) -> {
                showSnackBar("Missing email", true)
                false
            }

            TextUtils.isEmpty(registerPassword.text.toString().trim()) -> {
                showSnackBar("Missing password", true)
                false
            }

            // TODO: add condition to fail

            else -> {
                showSnackBar("valid detail")
                true
            }
        }
    }

}