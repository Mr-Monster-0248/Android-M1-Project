package fr.thibaultlepez.chill.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.services.registerWithEmailAndPassword
import java.lang.Error

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
        if (validateRegister()) {
            try {
                registerWithEmailAndPassword(registerEmail.text.toString(), registerPassword.text.toString()) { user ->
                    showSnackBar("Register success")
                }
            } catch (err: Error) {
                Log.e("CHILL", "Error while registering user", err)
            }
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