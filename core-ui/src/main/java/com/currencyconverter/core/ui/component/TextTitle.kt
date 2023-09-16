package com.currencyconverter.core.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.currencyconverter.core.ui.PrimaryColor
import com.currencyconverter.core.ui.Typography

@Composable
fun TextTitle(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = PrimaryColor,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = Typography.titleLarge
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(color = textColor)
    )
}
