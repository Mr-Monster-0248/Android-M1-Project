package fr.thibaultlepez.chill.store

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseUser
import fr.thibaultlepez.chill.models.*
import fr.thibaultlepez.chill.services.getSessionFromDb
import fr.thibaultlepez.chill.services.getUserFromDb
import fr.thibaultlepez.chill.services.retrieveMoviesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

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
                if (newFireSession != null && !sessions.contains(newFireSession)) sessions.add(newFireSession)
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


    suspend fun loadSessionInState(context: Context, sessionId: String) {
        val fireSession = getSessionFromDb(sessionId)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val useSystemLanguage = sharedPrefs.getBoolean("use_system_language", true)

        val lang =
            if (useSystemLanguage) Locale.getDefault().toLanguageTag();
            else sharedPrefs.getString("use_language_choice", "en-US")


        if (fireSession != null) {
            val movies = retrieveMoviesData(sessionId, if (lang.isNullOrBlank()) "en-US" else lang)
                .await()
            val users = fireSession.users
            val genres = fireSession.searchParams.query.with_genres

            this@State.session = Session(
                fireSession.id,
                fireSession.ownerId,
                fireSession.name,
                movies,
                users,
                genres,
                fireSession.state
            )

            Log.d("CHILL/STATE", this@State.session.toString())
        } else throw Error("Error while fetching session")
    }
}
