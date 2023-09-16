package com.currencyconverter.core.domain.mapper.responsemapper

import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.google.common.truth.Truth
import org.junit.Test


class CurrencyMapperForDataToDomainTest {

    private val currencyMapperForDataToDomain = CurrencyMapperForDataToDomain()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        val data = currenciesInput

        val expected = currenciesExpected

        val actual = currencyMapperForDataToDomain.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}

val currenciesInput = listOf(
    CurrencyModel("AED", "United Arab Emirates Dirham"),
    CurrencyModel("AFN", "Afghan Afghani"),
    CurrencyModel("ALL", "Albanian Lek")
)

val currenciesExpected = listOf(
    CurrencyUiModel("AED", "AED - United Arab Emirates Dirham"),
    CurrencyUiModel("AFN", "AFN - Afghan Afghani"),
    CurrencyUiModel("ALL", "ALL - Albanian Lek")
)