package fr.thibaultlepez.chill.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.models.Session
import fr.thibaultlepez.chill.services.updateMovieScore
import fr.thibaultlepez.chill.store.State
import kotlin.properties.Delegates

class SwipeActivity : BaseActivity() {

    private lateinit var synopsis: TextView
    private lateinit var title: TextView
    private lateinit var number: TextView
    private lateinit var poster: ImageView
    private var currentPos by Delegates.notNull<Int>()


    private lateinit var session: Session



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)


        synopsis = findViewById(R.id.swipe_activity_movie_synonpsis)
        title = findViewById(R.id.swipe_activity_movie_title)
        number = findViewById(R.id.swipe_activity_movie_number)
        poster = findViewById(R.id.swipe_activity_movie_poster)


        session = State.session!!
        currentPos = session.movies.indexOfFirst { movie -> movie.score == 0 }

        displayMovieData(currentPos)
    }


    private fun displayMovieData(pos: Int) {
        val movie = session.movies[pos]

        synopsis.text = movie.description
        title.text = "${movie.title} (${movie.year})"
        number.text = "Movie n°${session.movies.indexOf(movie)+1}/${session.movies.size}"
        setMoviePosterFromUrl(movie.poster_path)
    }


    private fun setMoviePosterFromUrl(posterUrl: String) {
        if (posterUrl.endsWith("/null")) {
            poster.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.img_background))
            return
        }

        val queue = Volley.newRequestQueue(this)

        val imageRequest = ImageRequest(
            posterUrl,
            { bitmap ->
                poster.setImageBitmap(bitmap)
                Log.d("CHILL/Fetched image", posterUrl)
            },
            300,
            600,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            { error -> Log.e("CHILL/Error loading poster", error.stackTraceToString()) }
        )

        queue.add(imageRequest)
    }



    fun wouldNotWatch(view: View) {
        showProgressDialog()
        session.movies[currentPos].wouldNotWatch()
        displayNextMovie()
        closeProgressDialog()
    }


    fun wouldWatch(view: View) {
        showProgressDialog()
        session.movies[currentPos].wouldWatch()
        displayNextMovie()
        closeProgressDialog()
    }


    fun wantToWatch(view: View) {
        showProgressDialog()
        session.movies[currentPos].wantToWatch()
        displayNextMovie()
        closeProgressDialog()
    }


    private fun displayNextMovie() {
        if (currentPos < session.movies.size-1) {
            currentPos++
            displayMovieData(currentPos)
        } else {
            handleEnd()
        }
    }



    private fun handleEnd() {
        val index = session.users.indexOfFirst { u -> u.id == State.user!!.id }
        session.users[index].done = true

        updateMovieScore(session.id, session.movies)

        goToSessionOverview()
    }



    private fun goToSessionOverview() {
        val intent =  Intent(this, SessionOverviewActivity::class.java)
        startActivity(intent)
        finish()
    }
}