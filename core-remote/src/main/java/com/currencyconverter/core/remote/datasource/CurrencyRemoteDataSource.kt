package com.currencyconverter.core.remote.datasource

import com.currencyconverter.core.remote.services.CurrencyConverterApiService
import javax.inject.Inject

interface CurrencyRemoteDataSource {
    suspend fun getCurrencies(): Map<String, String>
}

class CurrencyRemoteDataSourceImpl @Inject constructor(private val currencyConverterApiService: CurrencyConverterApiService) :
    CurrencyRemoteDataSource {
    override suspend fun getCurrencies(): Map<String, String> =
        currencyConverterApiService.getCurrencies()
}