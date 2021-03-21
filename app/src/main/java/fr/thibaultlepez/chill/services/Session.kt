package fr.thibaultlepez.chill.services

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson

fun removeUserFromSession(userId: String, sessionId: String) {
    val functions = FirebaseFunctions.getInstance()

    val data = hashMapOf(
        "userId" to userId,
        "sessionId" to sessionId
    )

    functions
        .getHttpsCallable("removeUserFromSession")
        .call(data)
}