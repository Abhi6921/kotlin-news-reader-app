package nl.narvekar.abhishek.student649744.utils

import android.content.Context
import android.content.SharedPreferences
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.utils.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.utils.Constants.IS_LOGGED_IN
import nl.narvekar.abhishek.student649744.utils.Constants.PASSWORD
import nl.narvekar.abhishek.student649744.utils.Constants.USERNAME

object Session {

    private lateinit var sharedPreferences: SharedPreferences

    fun startSession(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    }

    fun getAuthToken() : String {
        val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "") ?: ""
        return authToken
    }

    fun isUserLoggedIn() : Boolean {
        val loggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        return loggedIn
    }

    fun removeUserData() {
        sharedPreferences.edit().apply {
            putString(USERNAME, "")
            putString(PASSWORD, "")
            putString(AUTH_TOKEN_KEY, "")
            putBoolean(IS_LOGGED_IN, false)
        }.apply()
    }

    fun saveUserData(username: String, password: String, authToken: String) {
        sharedPreferences.edit().apply{
            putString(USERNAME, username)
            putString(PASSWORD, password)
            putString(AUTH_TOKEN_KEY, authToken)
            putBoolean(IS_LOGGED_IN, true)
        }.apply()
    }

}