package fr.thibaultlepez.chill.models

data class Session(
    val id: String,
    val ownerID: String,
    val name: String,
    val movies: ArrayList<Movie>,
    val users: ArrayList<FireSessionUser>,
    val genres: ArrayList<Int>,
    val state: Int
)
