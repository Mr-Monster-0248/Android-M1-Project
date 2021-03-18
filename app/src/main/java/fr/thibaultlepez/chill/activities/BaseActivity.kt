package fr.thibaultlepez.chill.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.thibaultlepez.chill.R

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog

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

    fun showProgressDialog() {
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        progressDialog.show()
    }

    fun closeProgressDialog() {
        progressDialog.dismiss()
    }
}