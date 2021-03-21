package fr.thibaultlepez.chill.models

import fr.thibaultlepez.chill.utils.Constants

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val description: String,
    val year: Int,
    var score: Int
) {
    fun wantToWatch() {
        score += Constants.WANT_TO_WATCH_SCORE
    }

    fun wouldWatch() {
        score += Constants.WOULD_WATCH_SCORE
    }

    fun wouldNotWatch() {
        score += Constants.WOULD_NOT_WATCH_SCORE
    }
}
