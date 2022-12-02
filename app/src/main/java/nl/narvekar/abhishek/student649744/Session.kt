package nl.narvekar.abhishek.student649744

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.MasterKeys
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.Constants.PASSWORD
import nl.narvekar.abhishek.student649744.Constants.USERNAME

object Session {

    private lateinit var sharedPreferences: SharedPreferences

    fun startSession(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    }

    fun getAuthToken() : String {
        val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, "") ?: ""
        return authToken
    }

    fun removeUserData() {
        sharedPreferences.edit().apply {
            putString(USERNAME, "")
            putString(PASSWORD, "")
            putString(AUTH_TOKEN_KEY, "")
        }.apply()
    }
    fun saveUserData(username: String, password: String, authToken: String) {
        sharedPreferences.edit().apply{
            putString(USERNAME, username)
            putString(PASSWORD, password)
            putString(AUTH_TOKEN_KEY, authToken)
        }.apply()
    }

}