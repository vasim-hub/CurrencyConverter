package com.currencyconverter.core.data.mapper.currencylist

import com.currencyconverter.core.data.mapper.MapperResponseToEntity
import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.database.entity.CurrencyEntity
import javax.inject.Inject

class CurrencyMapperForEntityToDataModel @Inject constructor() :
    MapperResponseToEntity<List<CurrencyEntity>, List<CurrencyModel>> {
    override fun invoke(data: List<CurrencyEntity>): List<CurrencyModel> = data.map {
        CurrencyModel(
            code = it.code,
            name = it.name
        )
    }
}