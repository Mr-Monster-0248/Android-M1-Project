package fr.thibaultlepez.chill.models

import fr.thibaultlepez.chill.utils.Constants

data class Movie(
    val id: String,
    val title: String,
    val posterURL: String,
    val description: String,
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
