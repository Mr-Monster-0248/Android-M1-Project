package fr.thibaultlepez.chill.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.services.updateUserUsername
import fr.thibaultlepez.chill.store.State

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this) ?: return
        setCurrentUsername()


        sharedPrefs.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "username") {
                val newUsername = sharedPreferences.all[key] as String
                State.user!!.username = newUsername
                updateUserUsername(newUsername)
            }
        }
    }



    private fun setCurrentUsername() {
        with (sharedPrefs.edit()) {
            putString("username", State.user!!.username)
            apply()
        }
    }



    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}