package fr.thibaultlepez.chill.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import fr.thibaultlepez.chill.models.FireSession
import fr.thibaultlepez.chill.models.FireUser
import fr.thibaultlepez.chill.models.User
import fr.thibaultlepez.chill.utils.DbConstants
import kotlinx.coroutines.tasks.await
import java.lang.Error

suspend fun getUserFromDb(userId: String): FireUser? {
    val db = FirebaseFirestore.getInstance()

    return try {
        val documentSnapshot = db.collection(DbConstants.USERS)
            .document(userId)
            .get()
            .await()

        documentSnapshot.toObject(FireUser::class.java)
    } catch (err: Error) {
        Log.e("CHILL/ERROR", err.stackTraceToString())
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
        Log.e("CHILL/ERROR", err.stackTraceToString())
        null
    }

}

