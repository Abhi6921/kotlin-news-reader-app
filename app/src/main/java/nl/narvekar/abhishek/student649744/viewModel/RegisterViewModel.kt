package nl.narvekar.abhishek.student649744.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.data.RegisterUserResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    fun signup(
        context: Context,
        user: User,
        navController: NavController
    ) {
        val retrofitInstance = NewsApi.getInstance()

        retrofitInstance.postANewUser(user).enqueue(object :
            Callback<RegisterUserResponse> {
            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                if (response.code() == 201) {
                    Toast.makeText(context, "Registeration Successful!", Toast.LENGTH_SHORT).show()

                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Register.route) {
                            inclusive = true
                        }
                    }
                }
                else {
                    Toast.makeText(context, "Registration failure: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        )
    }
}