package fr.thibaultlepez.chill.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Error

fun registerWithEmailAndPassword(email: String, password: String, success: (FirebaseUser) -> Unit ) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("CHILL", "Create a user with success")

                val user = auth.currentUser
                if (user != null) success(user)
            } else {
                Log.w("CHILL", "Error while creating user", task.exception)
                throw Error(task.exception)
            }
        }
}

fun loginWithEmailAndPassword(email: String, password: String, success: (FirebaseUser) -> Unit ) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("CHILL", "Log a user with success")

                val user = auth.currentUser
                if (user != null) success(user)
            } else {
                Log.w("CHILL", "Error while logging user", task.exception)
                throw Error(task.exception)
            }
        }
}

