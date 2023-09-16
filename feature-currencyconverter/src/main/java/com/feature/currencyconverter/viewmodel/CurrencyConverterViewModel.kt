package com.feature.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.core.domain.DomainStateHandler
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.domain.usecase.GetAllCurrenciesUseCase
import com.currencyconverter.core.domain.usecase.GetAllExchangeRateResultsUseCase
import com.feature.currencyconverter.ui.currency.CurrencyListUIState
import com.feature.currencyconverter.ui.currency.ExchangeRateListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    val getAllExchangeRateResultsUseCase: GetAllExchangeRateResultsUseCase,
) : ViewModel() {

    private val amountFlow = MutableStateFlow(0)
    val amountState: StateFlow<Int> = amountFlow

    private val sourceCurrencyFlow = MutableStateFlow<CurrencyUiModel?>(null)
    val sourceCurrencyState: StateFlow<CurrencyUiModel?> = sourceCurrencyFlow

    private val _allCurrenciesStateFlow =
        MutableStateFlow<CurrencyListUIState>(CurrencyListUIState.Empty)
    val allCurrenciesStateFlow: StateFlow<CurrencyListUIState> = _allCurrenciesStateFlow

    fun getAllCurrencies() {
        viewModelScope.launch {
            getAllCurrenciesUseCase(null).collectLatest { domainState ->
                when (domainState) {
                    is DomainStateHandler.Success -> {
                        _allCurrenciesStateFlow.value =
                            CurrencyListUIState.Success(domainState.data)
                    }

                    is DomainStateHandler.Loading -> {
                        _allCurrenciesStateFlow.value = CurrencyListUIState.Loading
                    }

                    is DomainStateHandler.Error -> {
                        _allCurrenciesStateFlow.value = CurrencyListUIState.Error(
                            domainState.message ?: "", domainState.errorStatusEnum
                        )
                    }

                    else -> {
                        _allCurrenciesStateFlow.value = CurrencyListUIState.Empty
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val allExchangeResults: StateFlow<ExchangeRateListUIState> =
        sourceCurrencyFlow.combine(amountFlow) { source, amount ->
            return@combine Pair(source, amount)
        }.filter {
            it.first != null && it.second > 0
        }.flatMapLatest {
                val pair = Pair(it.first?.code, it.second.toDouble())
                getAllExchangeRateResultsUseCase(pair).map { domainState ->
                    when (domainState) {
                        is DomainStateHandler.Success -> ExchangeRateListUIState.Success(domainState.data)
                        is DomainStateHandler.Loading -> ExchangeRateListUIState.Loading
                        is DomainStateHandler.Error -> {
                            ExchangeRateListUIState.Error(
                                domainState.message ?: "", domainState.errorStatusEnum
                            )
                        }

                        else -> ExchangeRateListUIState.Empty
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ExchangeRateListUIState.Empty
            )

    fun updateAmount(amount: String) {
        amount.toIntOrNull()?.let { amountFlow.value = it }
    }

    fun updateSourceCurrency(currency: CurrencyUiModel?) {
        sourceCurrencyFlow.value = currency
    }
}
