package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    sharedPreferences: SharedPreferences,
    loginViewModel: LoginViewModel
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

            Text(
                text = "Login",
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace)
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = "Username", color = MaterialTheme.colors.onSurface) },
                value = username.value,
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = "Password", color = MaterialTheme.colors.onSurface) },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        //onClickLogin(username.value.toString(), password.value.toString())
                        if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                            Toast.makeText(context, "please fill credentials!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
    //                        loginViewModel.loginUser(context,
    //                            User(
    //                                username.value.text,
    //                                password.value.text
    //                            ),
    //                            navController,
    //                            sharedPreferences
    //                        )
                            coroutineScope.launch {
                                loginViewModel.signIn(
                                    context, User(
                                        username.value.text,
                                        password.value.text
                                    ), navController, sharedPreferences
                                )
                            }

                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Login", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        navController.navigate(Routes.Register.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Register here", color = Color.White)
                }
            }
    }
}

//private fun loginUser(
//    context: Context,
//    user: User,
//    navController: NavController,
//    sharedPreferences: SharedPreferences
//) {
//    val retrofitInstance = NewsApi.getInstance()
//
//    retrofitInstance.loginUser(user).enqueue(
//        object : Callback<LoginResponse> {
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//
//                val authToken = response.body()?.AuthToken
//                //print(authToken)
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                editor.putString(AUTH_TOKEN_KEY, authToken)
//                editor.apply()
//                if (response.code() == 200) {
//                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
//
//                    navController.navigate(Routes.Home.route) {
//                        popUpTo(Routes.Login.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(context, "Login failure!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//}