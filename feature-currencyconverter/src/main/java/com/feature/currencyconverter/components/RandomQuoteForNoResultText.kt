package com.feature.currencyconverter.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.ui.Typography
import com.feature.currencyconverter.R
import kotlin.random.Random

@Composable
fun RandomQuoteForNoResultText() {

    val myStringArray = stringArrayResource(R.array.quotes)

    // Get a random index within the range of the array
    val randomIndex = remember {
        Random.nextInt(myStringArray.size)
    }

    // Access the random item from the array
    val randomQuote = remember {
        myStringArray[randomIndex]
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .align(
                    Alignment.Center
                ), text = "\"$randomQuote\"", style = Typography.titleLarge.copy(
                color = Color.Black, fontFamily = FontFamily.Monospace
            ), textAlign = TextAlign.Center
        )
    }
}


