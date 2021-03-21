package fr.thibaultlepez.chill.services

import com.google.firebase.functions.FirebaseFunctions
import fr.thibaultlepez.chill.models.FireMovie
import fr.thibaultlepez.chill.models.Movie
import fr.thibaultlepez.chill.utils.Constants

fun removeUserFromSession(userId: String, sessionId: String) {
    val functions = FirebaseFunctions.getInstance()

    val data = hashMapOf(
        "userId" to userId,
        "sessionId" to sessionId
    )

    functions
        .getHttpsCallable(Constants.REMOVE_USER_FROM_SESSION_FUNC)
        .call(data)
}


fun updateMovieScore(sessionId: String, movies: ArrayList<Movie>) {
    val functions = FirebaseFunctions.getInstance()

    val data = hashMapOf(
        "sessionId" to sessionId,
        "movies" to movies.map { m -> FireMovie(m.id, m.score) }
    )

    functions
        .getHttpsCallable(Constants.UPDATE_MOVIE_SCORE_FUNC)
        .call(data)
}