@file:OptIn(ExperimentalComposeUiApi::class)

package com.feature.currencyconverter.ui.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.shared.ErrorStatusEnum
import com.currencyconverter.core.ui.Color_EEEEEE
import com.currencyconverter.core.ui.component.AppDialog
import com.currencyconverter.core.ui.component.AppMainContainerScreen
import com.feature.currencyconverter.R
import com.feature.currencyconverter.components.ExchangeRateGridItem
import com.feature.currencyconverter.components.RandomQuoteForNoResultText
import com.feature.currencyconverter.components.ShimmerEffectForCurrencyLoading
import com.feature.currencyconverter.components.TopAppContent
import com.feature.currencyconverter.viewmodel.CurrencyConverterViewModel

@Composable
fun CurrencyConverterScreen(
    modifier: Modifier = Modifier,
    appTitle: String,
    viewModel: CurrencyConverterViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val listOfCurrencyUiModel = rememberSaveable { mutableStateOf(emptyList<CurrencyUiModel>()) }
    val listOfExchangeResultUiModel =
        rememberSaveable { mutableStateOf(emptyList<ExchangeResultUiModel>()) }

    val amountState by viewModel.amountState.collectAsStateWithLifecycle()
    val sourceCurrencyState by viewModel.sourceCurrencyState.collectAsStateWithLifecycle()

    val currenciesState by viewModel.allCurrenciesStateFlow.collectAsStateWithLifecycle()
    val exchangeResultsState by viewModel.allExchangeResults.collectAsStateWithLifecycle()
    val isLoading = remember { mutableStateOf(false) }
    var isNeedToShowErrorDialog by remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val errorErrorStatusEnum = remember { mutableStateOf(ErrorStatusEnum.API_ERROR) }

    LaunchedEffect(Unit) {
        viewModel.getAllCurrencies()
    }

    LaunchedEffect(currenciesState) {
        when (currenciesState) {
            is CurrencyListUIState.Success -> {
                isNeedToShowErrorDialog = false
                listOfCurrencyUiModel.value =
                    (currenciesState as CurrencyListUIState.Success).listOfCurrencyUiModel
            }

            is CurrencyListUIState.Error -> {
                isNeedToShowErrorDialog = true
                errorErrorStatusEnum.value =
                    (currenciesState as CurrencyListUIState.Error).errorStatusEnum
                errorMessage.value = (currenciesState as CurrencyListUIState.Error).message
            }

            else -> {
                // Implement other state
            }
        }

    }

    LaunchedEffect(exchangeResultsState) {
        when (exchangeResultsState) {
            ExchangeRateListUIState.Loading -> {
                isLoading.value = true
            }

            is ExchangeRateListUIState.Success -> {
                isLoading.value = false
                listOfExchangeResultUiModel.value =
                    (exchangeResultsState as ExchangeRateListUIState.Success).listOfExchangeRateUiModel

            }

            ExchangeRateListUIState.Empty -> {
                isLoading.value = false
                listOfExchangeResultUiModel.value = emptyList()
            }

            is ExchangeRateListUIState.Error -> {
                isLoading.value = false
                errorErrorStatusEnum.value =
                    (exchangeResultsState as ExchangeRateListUIState.Error).errorStatusEnum
                errorMessage.value = (exchangeResultsState as ExchangeRateListUIState.Error).message
            }
        }
    }

    if (errorMessage.value.isNotEmpty()) {
        AppDialog(
            dialogTitle = stringResource(R.string.error),
            dialogText = stringResource(R.string.no_internet_connection)
        ) {
            errorMessage.value = ""
        }
    }

    CurrencyConverterContent(
        modifier = modifier,
        isLoading = isLoading.value,
        appTitle = appTitle,
        currentAmount = amountState,
        currentCurrency = sourceCurrencyState,
        currencies = listOfCurrencyUiModel.value,
        exchangeResults = listOfExchangeResultUiModel.value,
        onAmountUpdated = viewModel::updateAmount,
        onCurrencyUpdated = {
            keyboardController?.hide()
            viewModel.updateSourceCurrency(it)
        }
    )
}

@Composable
fun CurrencyConverterContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    appTitle: String,
    currentAmount: Int,
    currentCurrency: CurrencyUiModel?,
    currencies: List<CurrencyUiModel>,
    exchangeResults: List<ExchangeResultUiModel>,
    onAmountUpdated: (String) -> Unit,
    onCurrencyUpdated: (CurrencyUiModel?) -> Unit
) {
    AppMainContainerScreen(
        appTitle = appTitle,
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color_EEEEEE)
            ) {

                TopAppContent(
                    modifier = modifier,
                    currentAmount = currentAmount,
                    currentCurrency = currentCurrency,
                    currencies = currencies,
                    onAmountUpdated = onAmountUpdated,
                    onCurrencyUpdated = onCurrencyUpdated
                )

                Spacer(modifier = Modifier.size(8.dp))

                if (isLoading) {
                    ShimmerEffectForCurrencyLoading()
                } else {
                    GridExchangeResults(exchangeResults)
                }
            }
        }
    )
}


@Composable
private fun GridExchangeResults(
    results: List<ExchangeResultUiModel>
) {
    if (results.isEmpty()) {
        RandomQuoteForNoResultText()
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(results) { item ->
                ExchangeRateGridItem(
                    item = item
                )
            }
        }
    }

}



