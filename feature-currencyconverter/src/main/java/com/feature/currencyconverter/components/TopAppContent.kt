package com.feature.currencyconverter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.ui.PrimaryColor

@Composable
fun TopAppContent(
    modifier: Modifier,
    currentAmount: Int,
    currentCurrency: CurrencyUiModel?,
    currencies: List<CurrencyUiModel>,
    onAmountUpdated: (String) -> Unit,
    onCurrencyUpdated: (CurrencyUiModel?) -> Unit
) {

    Column(
        modifier = modifier.background(
            PrimaryColor,
            RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        )
    ) {

        Spacer(modifier = Modifier.size(16.dp))

        TextFieldAmount(
            currentAmount = currentAmount,
            onAmountUpdated = onAmountUpdated
        )

        Spacer(modifier = Modifier.size(16.dp))

        TextFieldDropdownCurrency(
            currentCurrency = currentCurrency,
            currencies = currencies,
            onCurrencyUpdated = onCurrencyUpdated
        )

        Spacer(modifier = Modifier.size(32.dp))
    }
}