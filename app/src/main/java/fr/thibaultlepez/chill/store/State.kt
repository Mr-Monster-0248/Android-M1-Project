package fr.thibaultlepez.chill.store

import fr.thibaultlepez.chill.models.Session
import fr.thibaultlepez.chill.models.User

object State {
    var user: User? = null
    var sessions: ArrayList<Session> = ArrayList()
}