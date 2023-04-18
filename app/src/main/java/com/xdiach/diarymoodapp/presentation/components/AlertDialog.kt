package com.xdiach.diarymoodapp.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.xdiach.diarymoodapp.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onCloseDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (dialogOpened) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        onCloseDialog
                    }
                ) {
                    Text(text = stringResource(id = R.string.home_screen_yes))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onCloseDialog) {
                    Text(text = stringResource(id = R.string.home_screen_no))
                }
            },
            onDismissRequest = onCloseDialog
        )
    }
}
