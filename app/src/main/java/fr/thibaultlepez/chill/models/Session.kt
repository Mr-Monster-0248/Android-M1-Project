package fr.thibaultlepez.chill.models

data class Session(
    val id: String,
    val ownerID: String,
    val name: String,
    val movies: MutableList<Movie>,
    val users: MutableList<User>,
    val state: Int
)
