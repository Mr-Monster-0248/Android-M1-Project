package fr.thibaultlepez.chill.services

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import fr.thibaultlepez.chill.models.FireSession
import fr.thibaultlepez.chill.models.FireUser
import fr.thibaultlepez.chill.utils.DbConstants
import kotlinx.coroutines.tasks.await

suspend fun getUserFromDb(userId: String): FireUser? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val documentSnapshot = db.collection(DbConstants.USERS)
            .document(userId)
            .get()
            .await()

        documentSnapshot.toObject(FireUser::class.java)
    } catch (err: Error) {
        null
    }
}

suspend fun getSessionFromDb(sessionId: String): FireSession? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val documentSnapshot = db.collection(DbConstants.SESSIONS)
            .document(sessionId)
            .get()
            .await()

        documentSnapshot.toObject(FireSession::class.java)
    } catch (err: Error) {
        null
    }
}

fun saveSessionInDb(session: FireSession, callback: (Task<Void>) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    val sessionData = Gson().fromJson(
        Gson().toJson(session),
        HashMap::class.java
    )

    db.collection(DbConstants.SESSIONS)
        .document(session.id)
        .set(sessionData)
        .addOnCompleteListener(callback)
}

