package fr.thibaultlepez.chill.services

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson
import fr.thibaultlepez.chill.models.Movie


data class ApiResponse(val movies: ArrayList<Movie>) {}


fun retrieveMoviesData(sessionId: String, lang: String): Task<ArrayList<Movie>> {
    val functions = FirebaseFunctions.getInstance()

    val data = hashMapOf(
        "sessionId" to sessionId,
        "lang" to lang
    )

    return functions
        .getHttpsCallable("retrieveMoviesData")
        .call(data)
        .continueWith { task ->
            val result = task.result?.data as Map<String, Any>
            Gson().fromJson(
                Gson().toJson(result),
                ApiResponse::class.java
            ).movies
        }
}