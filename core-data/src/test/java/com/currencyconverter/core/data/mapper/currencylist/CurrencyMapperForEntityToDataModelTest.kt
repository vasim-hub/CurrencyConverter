package com.currencyconverter.core.data.mapper.currencylist

import com.currencyconverter.core.testing.fakedata.getListOfCurrencyEntities
import com.currencyconverter.core.testing.fakedata.getListOfCurrencyModels
import com.google.common.truth.Truth
import org.junit.Test

class CurrencyMapperForEntityToDataModelTest {

    private val currencyMapperForEntityToDataModel = CurrencyMapperForEntityToDataModel()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        val data = getListOfCurrencyEntities
        val expected = getListOfCurrencyModels

        val actual = currencyMapperForEntityToDataModel.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}