package nl.narvekar.abhishek.student649744.fragments

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nl.narvekar.abhishek.student649744.Constants.BASE_URL
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.RegisterUserResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.fragments.components.SuccessDialog
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.RegisterViewModel
import okhttp3.Route
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    registerViewModel: RegisterViewModel
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        SuccessDialog(setShowDialog = {
            showDialog.value = it
        })
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

       // val context = LocalContext.current

        Text(text = "Register here", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace))
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            label = { Text(text = "Username", color = MaterialTheme.colors.onSurface) },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = "Password", color = MaterialTheme.colors.onSurface) },
            value = password.value,
            onValueChange = { password.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                          if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                              Toast.makeText(context, "Please enter all credentials!", Toast.LENGTH_LONG)
                                  .show()
                          }
                          else {
                              registerViewModel.signup(
                                  context,
                                  User(
                                      username.value.text.toString(),
                                      password.value.text.toString()
                                  ),
                                  navController
                              )
                          }
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Register")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    navController.navigate(Routes.Login.route)
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
            ) {
                Text(text = "Back to Login")

            }
        }
    }
}
// https://medium.com/@yilmazvolkan/kotlin-sign-up-and-sign-in-with-retrofit-tutorial-c96ca14f06c4

//private fun signup(
//    context: Context,
//    user: User,
//    navController: NavController
//) {
//    //val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(NewsApi::class.java)
//    val retrofitInstance = NewsApi.getInstance()
//
//    retrofitInstance.postANewUser(user).enqueue(object :
//        Callback<RegisterUserResponse> {
//            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(
//                call: Call<RegisterUserResponse>,
//                response: Response<RegisterUserResponse>
//            ) {
//                if (response.code() == 201) {
//                    Toast.makeText(context, "Registeration Successful!", Toast.LENGTH_SHORT).show()
//
//                    navController.navigate(Routes.Login.route) {
//                        popUpTo(Routes.Register.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(context, "Registration failure", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    )
//}