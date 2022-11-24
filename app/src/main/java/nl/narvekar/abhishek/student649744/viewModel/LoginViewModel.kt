package nl.narvekar.abhishek.student649744.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.LoginResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun loginUser(context: Context,
              user: User,
              navController: NavController,
              sharedPreferences: SharedPreferences
    ) {
        val retrofitInstance = NewsApi.getInstance()
        viewModelScope.launch(Dispatchers.IO) {
            retrofitInstance.loginUser(user).enqueue(
                object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        val authToken = response.body()?.AuthToken

                        if (response.code() == 200 && response.isSuccessful) {
                            //print(authToken)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString(AUTH_TOKEN_KEY, authToken).toString()
                            editor.apply()

                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                        else {
                            Toast.makeText(context, "Login failure!", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            delay(6000)
        }

    }

    fun logout(
        sharedPreferences: SharedPreferences,
        navController: NavController
    ) {
        // clear authToken
        val editor = sharedPreferences.edit()
        editor.putString(AUTH_TOKEN_KEY, "")
        editor.clear()
        editor.apply()

        navController.navigate(Routes.Login.route) {
            popUpTo(Routes.Home.route) {
                inclusive = true
            }
        }
    }
}