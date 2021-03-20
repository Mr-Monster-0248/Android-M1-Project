package fr.thibaultlepez.chill.services

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions


data class MovieData(
    val id: String,
    val title: String,
    val poster_path: String,
    val description: String,
    val year: String,
    val score: Int
) {
    companion object {
        fun from(map: HashMap<String, Any>) = object {
            val id by map
            val title by map
            val poster_path by map
            val description by map
            val year by map
            val score by map

            val data = MovieData(
                (id as Int).toString(),
                title as String,
                poster_path as String,
                description as String,
                year as String,
                score as Int
            );
        }.data
    }
};


fun retrieveMoviesData(sessionId: String, lang: String): Task<ArrayList<HashMap<String, Any>>> {
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
            result["movies"] as ArrayList<HashMap<String, Any>>
        }
}