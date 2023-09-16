package com.currencyconverter.core.domain.mapper.responsemapper

import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.domain.mapper.MapperDataToDomain
import com.currencyconverter.core.domain.model.CurrencyUiModel
import javax.inject.Inject

class CurrencyMapperForDataToDomain @Inject constructor() :
    MapperDataToDomain<List<CurrencyModel>, List<CurrencyUiModel>> {
    override fun invoke(data: List<CurrencyModel>): List<CurrencyUiModel> = data.map {
        CurrencyUiModel(
            code = it.code,
            text = "${it.code} - ${it.name}"
        )
    }
}