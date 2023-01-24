package nl.narvekar.abhishek.student649744.fragments

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.LoginViewModel

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        var passwordVisible by remember { mutableStateOf(false) }

            Text(
                text = stringResource(R.string.ui_text_login_title),
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace)
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.ui_username_label), color = MaterialTheme.colors.onSurface) },
                value = username.value,
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.ui_password_label), color = MaterialTheme.colors.onSurface) },
                value = password.value,
                visualTransformation = if (passwordVisible) { VisualTransformation.None } else { PasswordVisualTransformation() },
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    val description = if (passwordVisible) "Hide password" else "show password"
                    IconButton(onClick = { passwordVisible =! passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description, modifier = Modifier
                            .size(50.dp)
                            .padding(top = 10.dp, bottom = 10.dp))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        //onClickLogin(username.value.toString(), password.value.toString())
                        if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                            Toast.makeText(context, context.getString(R.string.ui_empty_fields_error), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            coroutineScope.launch {
                                loginViewModel.signIn(
                                    context, User(
                                        username.value.text,
                                        password.value.text
                                    ), navController
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
                    Text(text = stringResource(R.string.ui_text_login_button), color = Color.White)
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
                    Text(text = stringResource(R.string.ui_register_here_button_text), color = Color.White)
                }
            }
    }
}