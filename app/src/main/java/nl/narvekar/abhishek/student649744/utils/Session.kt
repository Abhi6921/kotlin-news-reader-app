package nl.narvekar.abhishek.student649744.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.utils.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.utils.Constants.IS_LOGGED_IN
import nl.narvekar.abhishek.student649744.utils.Constants.PASSWORD
import nl.narvekar.abhishek.student649744.utils.Constants.USERNAME


object Session {

    private lateinit var sharedPreferences: EncryptedSharedPreferences

    fun startSession(context: Context) {

        val masterKey =  MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

         sharedPreferences = EncryptedSharedPreferences.create(
            context,
            context.getString(R.string.app_name),
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
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
        sharedPreferences.edit().apply {
            putString(USERNAME, username)
            putString(PASSWORD, password)
            putString(AUTH_TOKEN_KEY, authToken)
            putBoolean(IS_LOGGED_IN, true)
        }.apply()
    }
}