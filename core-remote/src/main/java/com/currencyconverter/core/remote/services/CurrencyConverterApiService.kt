package com.currencyconverter.core.remote.services

import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse
import com.currencyconverter.core.remote.utils.RequireHeaders
import retrofit2.http.GET

interface CurrencyConverterApiService {

    // As in free account can't change base
    @RequireHeaders
    @GET("latest.json")
    suspend fun getCurrentExchangeRate(): ExchangeRateCurrencyResponse

    @GET("currencies.json")
    suspend fun getCurrencies(): Map<String, String>
}
