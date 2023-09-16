package com.currencyconverter.core.remote.model

data class ExchangeRateCurrencyResponse(
    val base: String,
    val rates: Map<String, Double>
)