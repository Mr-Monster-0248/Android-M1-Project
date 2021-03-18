package fr.thibaultlepez.chill.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.thibaultlepez.chill.R

open class BaseActivity : AppCompatActivity() {

    fun showSnackBar(message: String, error: Boolean = false) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (error) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.red_bittersweet
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.green_aquamarine
                )
            )
        }

        snackBar.show()
    }
}