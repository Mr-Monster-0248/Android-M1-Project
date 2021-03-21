package fr.thibaultlepez.chill.store

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import fr.thibaultlepez.chill.models.FireSession
import fr.thibaultlepez.chill.models.Session
import fr.thibaultlepez.chill.models.User
import fr.thibaultlepez.chill.services.getSessionFromDb
import fr.thibaultlepez.chill.services.getUserFromDb

object State {
    var user: User? = null
    var sessions: ArrayList<FireSession> = ArrayList()
    var session: Session? = null

    suspend fun updateState(fireAuthUser: FirebaseUser) {

        val fireUser = getUserFromDb(fireAuthUser.uid)

        if (fireUser != null) {
            user = User(
                fireUser.id,
                if (fireAuthUser.email != null) fireAuthUser.email else "",
                fireUser.username
            )

            fireUser.sessionIds.forEach {
                val newFireSession = getSessionFromDb(it)
                if (newFireSession != null && !sessions.contains(newFireSession))
                    sessions.add(newFireSession)
                else throw Error("Error while fetching session")
            }
        } else throw Error("Error while fetching user")

        Log.i("CHILL", "State has been initiated")

    }

    fun reset() {
        user = null
        sessions = ArrayList()
        session = null
    }

    override fun toString(): String {
        return "STATE:\nuser=${user.toString()}\nsession=${session}"
    }
}