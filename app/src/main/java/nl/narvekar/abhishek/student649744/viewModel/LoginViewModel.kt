package nl.narvekar.abhishek.student649744.viewModel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.utils.Session
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.LoginResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val retrofit = RetrofitInstance.getInstance()

    suspend fun loginUser(context: Context,
              user: User,
              navController: NavController
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            retrofit.loginUser(user).enqueue(
                object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        if (response.code() == 200 && response.isSuccessful) {

                              val authToken = response.body()?.AuthToken

                            if (authToken != null) {
                                storeUserCredentials(user.UserName, user.Password, authToken)
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
        }
    }

    suspend fun signIn(context: Context, user: User, navController: NavController) {
        loginUser(context, user, navController)
    }

    suspend fun logout(
        navController: NavController
    ) {
        Session.removeUserData()

        navController.navigate(Routes.Login.route) {
            popUpTo(Routes.Home.route) {
                inclusive = true
            }
        }
    }

    suspend fun signOut(navController: NavController) {
        logout(navController)
    }

    private fun storeUserCredentials(username: String, password: String, authToken: String) {
        Session.saveUserData(username, password, authToken)
    }
}

