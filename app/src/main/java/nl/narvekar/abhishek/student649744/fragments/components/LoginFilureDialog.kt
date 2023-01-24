package nl.narvekar.abhishek.student649744.fragments.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun LoginFailureDialog(alertBoxDialog: MutableState<Boolean>) {

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = {
                alertBoxDialog.value = false
            }) {
                Text(text = "OK")
            }
        },
        title = { Text(text = "Login Failure!", fontWeight = FontWeight.Bold, fontSize = 19.sp) },
        text = { Text(text = "Incorrect Username or Password!, Please check your details and try again!")}
    )
}