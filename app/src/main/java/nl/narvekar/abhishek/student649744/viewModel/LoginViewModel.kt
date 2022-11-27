package nl.narvekar.abhishek.student649744.viewModel


import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.Session
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.LoginResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    suspend fun loginUser(context: Context,
              user: User,
              navController: NavController
    ) {
        val retrofitInstance = NewsApi.getInstance()
        viewModelScope.launch(Dispatchers.IO) {
            retrofitInstance.loginUser(user).enqueue(
                object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        if (response.code() == 200 && response.isSuccessful) {

                              val authToken = response.body()?.AuthToken

                            if (authToken != null) {
                                saveData(user.UserName, user.Password, authToken)
                            }

                            Toast.makeText(context, context.getString(nl.narvekar.abhishek.student649744.R.string.ui_login_successful_message), Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                        else {
                            Toast.makeText(context, context.getString(nl.narvekar.abhishek.student649744.R.string.ui_login_failure_message), Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            delay(6000)
        }
    }

    suspend fun signIn(context: Context, user: User, navController: NavController) {
        loginUser(context, user, navController)
    }

    suspend fun logout(
        navController: NavController
    ) {
        Session.removeData()

        navController.navigate(Routes.Login.route) {
            popUpTo(Routes.Home.route) {
                inclusive = true
            }
        }
    }

    suspend fun signOut(navController: NavController) {
        logout(navController)
    }

    private fun saveData(username: String, password: String, authToken: String) {
        Session.saveData(username, password, authToken)
    }
}

