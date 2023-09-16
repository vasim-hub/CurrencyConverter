@file:OptIn(ExperimentalMaterial3Api::class)

package com.currencyconverter.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.currencyconverter.core.ui.CurrencyConverterTheme
import com.currencyconverter.core.ui.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMainContainerScreen(
    modifier: Modifier = Modifier,
    appTitle: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextTitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = appTitle,
                        textColor = Color.White,
                        textAlign = TextAlign.Left
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryColor,
                    titleContentColor = PrimaryColor
                )
            )
        },
        modifier = modifier,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun ScaffoldPreview() {
    CurrencyConverterTheme {
        AppMainContainerScreen(
            appTitle = "",
            content = {}
        )
    }
}
