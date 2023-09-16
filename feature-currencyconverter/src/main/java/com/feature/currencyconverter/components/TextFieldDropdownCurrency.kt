package com.feature.currencyconverter.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.ui.Typography
import com.currencyconverter.core.ui.component.AutoCompleteBox
import com.currencyconverter.core.ui.component.CustomTextField
import com.currencyconverter.core.ui.component.TextTitle
import com.feature.currencyconverter.R

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldDropdownCurrency(
    currentCurrency: CurrencyUiModel?,
    currencies: List<CurrencyUiModel>,
    onCurrencyUpdated: (CurrencyUiModel?) -> Unit
) {
    AutoCompleteBox(items = currencies, itemContent = { currency ->
        TextTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            text = currency.text,
            textColor = Color.Black,
            style = Typography.labelMedium,
            textAlign = TextAlign.Start
        )
    }) {

        val keyboardController = LocalSoftwareKeyboardController.current
        var value by remember { mutableStateOf(currentCurrency?.text.orEmpty()) }
        val view = LocalView.current

        val keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
        )

        onItemSelected { currency ->
            onCurrencyUpdated(currency)
            value = currency.text
            filter(value)
            view.clearFocus()
        }

        CustomTextField(value = value,
            label = stringResource(id = R.string.select_currency),
            onValueChanged = { query ->
                value = query
                filter(value)
            },
            onClearClick = {
                value = ""
                filter(value)
                view.clearFocus()
                onCurrencyUpdated(null)
            },
            onFocusChanged = { focusState ->
                isSearching = focusState.isFocused
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }))
    }
}