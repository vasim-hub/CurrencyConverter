package com.currencyconverter.core.domain.mapper.responsemapper

import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.google.common.truth.Truth
import org.junit.Test

class ExchangeRateMapperForDataToDomainTest {

    private val exchangeRateMapperForDataToDomain = ExchangeRateMapperForDataToDomain()

    @Test
    fun mapCategoryItemDtoList_returnCategoryItemList() {

        exchangeRateMapperForDataToDomain.source = "ABC"
        exchangeRateMapperForDataToDomain.amount = 10_000.0

        val data = ratesInput

        val expected = ratesExpected

        val actual = exchangeRateMapperForDataToDomain.invoke(data)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}

val ratesInput = listOf(
    ExchangeRateModel("CHF", "USD", 0.92006),
    ExchangeRateModel("BND", "USD", 1.348212),
    ExchangeRateModel("ABC", "USD", 3.0)
)

val ratesExpected = listOf(
    ExchangeResultUiModel("${"CHF"} ${"CHF".toFlagEmoji()}", "3,066.87"),
    ExchangeResultUiModel("${"BND"} ${"BND".toFlagEmoji()}", "4,494.04"),
    ExchangeResultUiModel("${"ABC"} ${"ABC".toFlagEmoji()}", "10,000")
)