package fr.thibaultlepez.chill.services

import com.google.firebase.functions.FirebaseFunctions
import fr.thibaultlepez.chill.utils.Constants


fun updateUserUsername(newUsername: String) {
    val functions = FirebaseFunctions.getInstance()

    val data = hashMapOf(
        "newUsername" to newUsername
    )

    functions
        .getHttpsCallable(Constants.UPDATE_USER_USERNAME_FUNC)
        .call(data)
}