package fr.thibaultlepez.chill.models

data class FireMovie(
    val id: Int,
    val score: Int
)

data class FireQuery(
    val include_adult: Boolean,
    val with_genres: List<Int>
)

data class FireSearchParams(
    val max_nbr: Int,
    val query: FireQuery
)

data class FireSession(
    val id: String,
    val name: String,
    val ownerId: String,
    val movies: List<FireMovie>,
    val searchParams: FireSearchParams,
    val state: Int,
    val user: FireUser
)

data class FireUser(
    val id: String = "",
    val sessionIds: List<String> = ArrayList(),
    val username: String = ""
)

