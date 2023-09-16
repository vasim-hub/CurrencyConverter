package com.currencyconverter.core.data.mapper.exchangerate

import com.currencyconverter.core.data.mapper.MapperResponseToEntity
import com.currencyconverter.core.database.entity.ExchangeRatesEntity
import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse
import javax.inject.Inject

class ExchangeRateMapperForResponseToEntity @Inject constructor() :
    MapperResponseToEntity<ExchangeRateCurrencyResponse, List<ExchangeRatesEntity>> {
    override fun invoke(data: ExchangeRateCurrencyResponse): List<ExchangeRatesEntity> =
        data.rates.map {
            ExchangeRatesEntity(
                code = it.key,
                base = data.base,
                rate = it.value
            )
        }
}