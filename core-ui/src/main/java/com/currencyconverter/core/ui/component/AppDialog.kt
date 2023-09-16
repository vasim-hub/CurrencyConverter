package com.currencyconverter.core.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.currencyconverter.core.ui.R

@Composable
fun AppDialog(
    dialogTitle: String,
    dialogText: String,
    onDismissRequest: (Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest(true)
        },
        containerColor = Color.White,
        title = { Text(dialogTitle) },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest(true)
            }) {
                Text(stringResource(R.string.close).uppercase())
            }
        }
    )
}