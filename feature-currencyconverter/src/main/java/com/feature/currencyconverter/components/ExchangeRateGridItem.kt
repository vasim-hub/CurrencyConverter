package com.feature.currencyconverter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.ui.CurrencyConverterTheme


@Composable
fun ExchangeRateGridItem(
    modifier: Modifier = Modifier, item: ExchangeResultUiModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White, shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White, shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = item.code,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = item.amount,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            maxLines = 1
        )
    }
}

@Preview(widthDp = 200)
@Composable
private fun ExchangeResultPreview() {
    CurrencyConverterTheme {
        ExchangeRateGridItem(
            item = ExchangeResultUiModel("QWE", "123,123.00")
        )
    }
}

