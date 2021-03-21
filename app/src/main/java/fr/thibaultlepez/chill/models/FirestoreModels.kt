package fr.thibaultlepez.chill.models

data class FireMovie(
    val id: Int = 0,
    val score: Int = 0
)

data class FireQuery(
    val include_adult: Boolean = false,
    val with_genres: ArrayList<Int> = ArrayList()
)

data class FireSearchParams(
    val max_nbr: Int = 0,
    val query: FireQuery = FireQuery()
)

data class FireSessionUser(
    val username: String = "",
    val id: String = "",
    val done: Boolean = false
)

data class FireSession(
    val id: String = "",
    val name: String = "",
    val ownerId: String = "",
    val movies: ArrayList<FireMovie> = ArrayList(),
    val users: ArrayList<FireSessionUser> = ArrayList(),
    val searchParams: FireSearchParams = FireSearchParams(),
    val state: Int = 0
)

data class FireUser(
    val id: String = "",
    val sessionIds: ArrayList<String> = ArrayList(),
    val username: String = ""
)

