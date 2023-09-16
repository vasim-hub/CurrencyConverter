package com.currencyconverter.core.testing.fakedata

import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.database.entity.ExchangeRatesEntity
import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse

const val baseCurrency = "USD"

val exchangeRateDataMap = mapOf(
    "AED" to 3.673035,
    "AFN" to 84.568993,
    "ALL" to 94.11873,
    "AMD" to 384.437618,
    "ANG" to 1.802802,
    "AOA" to 824.936,
    "ARS" to 279.3,
    "AUD" to 1.5186,
    "AWG" to 1.8025,
    "AZN" to 1.7,
    "BAM" to 1.773727,
    "BBD" to 2.0,
    "BDT" to 108.184829,
    "BGN" to 1.773727,
    "BHD" to 0.377026,
    "BIF" to 2833.574879,
    "BMD" to 1.0,
    "BND" to 1.333696,
    "BOB" to 6.912341,
    "BRL" to 4.8734
)

val getListOfExchangeRatesEntities = exchangeRateDataMap.entries.map { (code, rate) ->
    ExchangeRatesEntity(
        code = code,
        base = baseCurrency,
        rate = rate
    )
}


val getListOfExchangeRatesModels = exchangeRateDataMap.entries.map { (code, rate) ->
    ExchangeRateModel(
        code = code,
        base = baseCurrency,
        rate = rate
    )
}

val exchangeRateCurrencyResponse =
    ExchangeRateCurrencyResponse(base = baseCurrency, rates = exchangeRateDataMap)


