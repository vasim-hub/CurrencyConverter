package com.currencyconverter.core.data.mapper.currencylist

import com.currencyconverter.core.testing.fakedata.currencyMap
import com.currencyconverter.core.testing.fakedata.getListOfCurrencyEntities
import com.google.common.truth.Truth
import org.junit.Test

class CurrencyMapperForResponseToEntityTest {

    private val currencyMapperForResponseToEntity = CurrencyMapperForResponseToEntity()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        val data = currencyMap
        val expected = getListOfCurrencyEntities

        val actual = currencyMapperForResponseToEntity.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}