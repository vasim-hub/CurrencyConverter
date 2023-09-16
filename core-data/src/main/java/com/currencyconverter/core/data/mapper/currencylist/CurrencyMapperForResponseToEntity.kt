package com.currencyconverter.core.data.mapper.currencylist

import com.currencyconverter.core.data.mapper.MapperResponseToEntity
import com.currencyconverter.core.database.entity.CurrencyEntity
import javax.inject.Inject

class CurrencyMapperForResponseToEntity @Inject constructor() :
    MapperResponseToEntity<Map<String, String>, List<CurrencyEntity>> {
    override fun invoke(data: Map<String, String>): List<CurrencyEntity> = data.map {
        CurrencyEntity(
            code = it.key,
            name = it.value
        )
    }
}