package fr.thibaultlepez.chill.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import fr.thibaultlepez.chill.models.FireSession
import fr.thibaultlepez.chill.models.FireUser
import fr.thibaultlepez.chill.models.User
import fr.thibaultlepez.chill.utils.DbConstants
import kotlinx.coroutines.runBlocking

fun getUserFromDb(userId: String, callback: (FireUser) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection(DbConstants.USERS)
        .document(userId)
        .get()
        .addOnSuccessListener {
            val user = it.toObject(FireUser::class.java)
            if (user != null) callback(user)
        }
        .addOnFailureListener {
            Log.e("CHILL/fail on DB", it.stackTraceToString())
            throw it
        }
}

fun getSessionFromDb(sessionId: String, callback: (FireSession) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection(DbConstants.SESSIONS)
        .document(sessionId)
        .get()
        .addOnSuccessListener {
            val session = it.toObject(FireSession::class.java)
            if (session != null ) callback(session)
        }
        .addOnFailureListener {
            Log.e("CHILL/fail on DB", it.stackTraceToString())
            throw it
        }
}

