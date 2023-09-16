package com.currencyconverter.core.data.mapper.exchangerate

import com.currencyconverter.core.testing.fakedata.getListOfExchangeRatesEntities
import com.currencyconverter.core.testing.fakedata.getListOfExchangeRatesModels
import com.google.common.truth.Truth
import org.junit.Test

class ExchangeRateMapperForResponseToEntityTest {

    private val exchangeRateMapperForEntityToDataModel = ExchangeRateMapperForEntityToDataModel()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        val data = getListOfExchangeRatesEntities

        val expected = getListOfExchangeRatesModels

        val actual = exchangeRateMapperForEntityToDataModel.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}