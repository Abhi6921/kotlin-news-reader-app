package nl.narvekar.abhishek.student649744.fragments.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import nl.narvekar.abhishek.student649744.R
import nl.narvekar.abhishek.student649744.navigation.Routes
import okhttp3.Route


@Composable
fun SuccessDialog(setShowDialog: (Boolean) -> Unit) {

    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = stringResource(R.string.ui_dialog_title_text))
        },
        text = {
            Text(text = stringResource(R.string.ui_success_dialog_message))
        },
        confirmButton = {
            TextButton(onClick = {
                setShowDialog(false)
            }) {
                Text(text = stringResource(R.string.ui_dismiss_button_text))
            }
        }
    )
}