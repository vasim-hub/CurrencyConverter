package com.feature.currencyconverter.ui.currency

import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.shared.ErrorStatusEnum

sealed class CurrencyListUIState {
    object Loading : CurrencyListUIState()
    data class Success(val listOfCurrencyUiModel: List<CurrencyUiModel>) : CurrencyListUIState()
    object Empty : CurrencyListUIState()
    data class Error(val message: String, val errorStatusEnum: ErrorStatusEnum) :
        CurrencyListUIState()
}


sealed class ExchangeRateListUIState {
    object Loading : ExchangeRateListUIState()
    data class Success(val listOfExchangeRateUiModel: List<ExchangeResultUiModel>) :
        ExchangeRateListUIState()

    object Empty : ExchangeRateListUIState()
    data class Error(val message: String, val errorStatusEnum: ErrorStatusEnum) :
        ExchangeRateListUIState()
}