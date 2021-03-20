package fr.thibaultlepez.chill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.models.*
import fr.thibaultlepez.chill.store.State
import fr.thibaultlepez.chill.utils.Constants
import java.security.Provider
import java.util.*
import java.util.UUID.randomUUID
import kotlin.collections.ArrayList



data class Genre(val id: String, val name: String) {}
data class ApiResponse(val genres: ArrayList<Genre>) {}



class EditActivity : AppCompatActivity() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var nbrMenu: AutoCompleteTextView
    private lateinit var adultSwitch: SwitchMaterial
    private lateinit var chipGroup: ChipGroup


    private var selectedName = ""
    private var selectedGenres = ArrayList<Int>()
    private var selectedNbr = 0
    private var adultIncluded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        nameInput = findViewById(R.id.edit_session_name)
        nbrMenu = findViewById(R.id.menu_max_nbr)
        adultSwitch = findViewById(R.id.edit_activity_include_adult)
        chipGroup = findViewById(R.id.chip_group_categories)

        populateDropdownList()
        fetchMovieGenres()

        nameInput.addTextChangedListener { selectedName = it.toString() }
    }


    fun createSession(view: View) {
        val searchParams = FireSearchParams(selectedNbr, FireQuery(adultIncluded, selectedGenres))
        val sessionId = selectedName + randomUUID().toString().substring(0, 3)
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val sessionMovies = ArrayList<FireMovie>()
        val sessionUsers = ArrayList<FireSessionUser>()

        sessionUsers.add(FireSessionUser(State.user!!.id, State.user!!.username))


        val newSession = FireSession(
            sessionId,
            selectedName,
            ownerId,
            sessionMovies,
            sessionUsers,
            searchParams,
            SessionState.NEW
        )


        Log.d("CHILL/Creating new Session", newSession.toString())
        // TODO: Call cloud function
    }



    private fun populateDropdownList() {
        val nbrFilmOptions = resources.getIntArray(R.array.nbr_films).asList()
        val adapter = ArrayAdapter(this, R.layout.list_item, nbrFilmOptions)
        nbrMenu.setAdapter(adapter)
        nbrMenu.setOnItemClickListener { adapterView, view, i, l ->
            selectedNbr = nbrFilmOptions[i]
            Log.d("CHILL/Dropdown value", nbrFilmOptions[i].toString())
        }
    }



    private fun fetchMovieGenres() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val useSystemLanguage = sharedPrefs.getBoolean("use_system_language", true)

        val lang =
            if (useSystemLanguage) Locale.getDefault().toLanguageTag();
            else sharedPrefs.getString("use_language_choice", "en-US")

        val queryUrl = "https://api.themoviedb.org/3/genre/movie/list?" +
            "api_key=" + Constants.TMDB_API_KEY +
            "&language=" + lang


        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, queryUrl,
            { response ->
                val genres = Gson()
                    .fromJson(response, ApiResponse::class.java)
                    .genres
                Log.d("CHILL/Genres list", genres.toString())

                populateChipGroup(genres)
            },
            { Log.d("CHILL/Genres","Retrieval failed") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }



    private fun populateChipGroup(genres: ArrayList<Genre>) {
        genres.forEach { genre ->
            val chip = Chip(this)
            chip.text = genre.name
            chip.id = genre.id.toInt()
            chip.isCheckable = true
            chip.isSelected = false

            chip.setOnClickListener { handleChipClick(it as Chip) }

            this.chipGroup.addView(chip)
        }
    }



    private fun handleChipClick(chip: Chip) {
        chip.isSelected = !chip.isSelected

        if (chip.isSelected && !selectedGenres.contains(chip.id)) selectedGenres.add(chip.id)
        else if (selectedGenres.contains(chip.id) && !chip.isSelected) selectedGenres.remove(chip.id)
    }



    fun handleAdultSwitch(view: View) {
        adultIncluded = adultSwitch.isChecked
    }
}