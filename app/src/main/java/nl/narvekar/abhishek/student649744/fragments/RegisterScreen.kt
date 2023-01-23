package nl.narvekar.abhishek.student649744.fragments

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.data.User
import nl.narvekar.abhishek.student649744.fragments.components.SuccessDialog
import nl.narvekar.abhishek.student649744.navigation.Routes
import nl.narvekar.abhishek.student649744.viewModel.RegisterViewModel


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    //val showDialog = remember { mutableStateOf(false) }
    val showDialog = registerViewModel.showMessageOnRegister
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

        Text(text = stringResource(R.string.ui_register_title), style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Monospace))
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            label = { Text(text = stringResource(R.string.ui_username_label), color = MaterialTheme.colors.onSurface) },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.ui_password_label), color = MaterialTheme.colors.onSurface) },
            value = password.value,
            onValueChange = { password.value = it }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                          if (username.value.text.isEmpty() || password.value.text.isEmpty()) {
                              Toast.makeText(context, context.getString(R.string.ui_empty_fields_error), Toast.LENGTH_LONG)
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
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.ui_register_button_text))
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
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = stringResource(R.string.ui_back_to_login_text))

            }
        }
    }
}