package com.currencyconverter.core.data.mapper.exchangerate

import com.currencyconverter.core.data.mapper.MapperResponseToEntity
import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.database.entity.ExchangeRatesEntity
import javax.inject.Inject

class ExchangeRateMapperForEntityToDataModel @Inject constructor() :
    MapperResponseToEntity<List<ExchangeRatesEntity>, List<ExchangeRateModel>> {
    override fun invoke(data: List<ExchangeRatesEntity>): List<ExchangeRateModel> = data.map {
        ExchangeRateModel(
            code = it.code,
            base = it.base,
            rate = it.rate
        )
    }
}
