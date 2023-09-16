package com.currencyconverter.core.data.mapper.exchangerate

import com.currencyconverter.core.testing.fakedata.exchangeRateCurrencyResponse
import com.currencyconverter.core.testing.fakedata.getListOfExchangeRatesEntities
import com.google.common.truth.Truth
import org.junit.Test

class ExchangeRateMapperForEntityToDataModelTest {

    private val exchangeRateMapperForResponseToEntity = ExchangeRateMapperForResponseToEntity()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        val data = exchangeRateCurrencyResponse

        val expected = getListOfExchangeRatesEntities

        val actual = exchangeRateMapperForResponseToEntity.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}