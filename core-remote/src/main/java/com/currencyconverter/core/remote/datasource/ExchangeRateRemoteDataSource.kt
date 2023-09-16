package com.currencyconverter.core.remote.datasource

import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse
import com.currencyconverter.core.remote.services.CurrencyConverterApiService
import javax.inject.Inject

interface ExchangeRateRemoteDataSource {
    suspend fun getLatestExchangeRate(): ExchangeRateCurrencyResponse
}

class ExchangeRateRemoteDataSourceImpl @Inject constructor(private val currencyConverterApiService: CurrencyConverterApiService) :
    ExchangeRateRemoteDataSource {
    override suspend fun getLatestExchangeRate(): ExchangeRateCurrencyResponse =
        currencyConverterApiService.getCurrentExchangeRate()
}