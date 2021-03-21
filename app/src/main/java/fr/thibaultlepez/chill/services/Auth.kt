package fr.thibaultlepez.chill.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun registerWithEmailAndPassword(email: String, password: String, success: (FirebaseUser) -> Unit ) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) success(user)
            } else {
                throw Error(task.exception)
            }
        }
}

fun loginWithEmailAndPassword(email: String, password: String, success: (FirebaseUser) -> Unit ) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) success(user)
            } else {
                throw Error(task.exception)
            }
        }
}

