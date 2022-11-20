package nl.narvekar.abhishek.student649744.fragments

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nl.narvekar.abhishek.student649744.Constants.AUTH_TOKEN_KEY
import nl.narvekar.abhishek.student649744.api.NewsApi
import nl.narvekar.abhishek.student649744.api.RetrofitInstance
import nl.narvekar.abhishek.student649744.data.LoginResponse
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.fragments.components.ProgressBarLoading
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.ui.theme.Purple700
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    sharedPreferences: SharedPreferences,
    loginViewModel: LoginViewModel
) {
    // Commit from login code refactor branch
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        val context = LocalContext.current

        Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace))

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = "Username") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    //onClickLogin(username.value.toString(), password.value.toString())
                    if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                        Toast.makeText(context, "please fill credentials!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        loginViewModel.loginUser(context,
                            User(
                                username.value.text.toString(),
                                password.value.text.toString()
                            ),
                            navController,
                            sharedPreferences
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
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
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
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