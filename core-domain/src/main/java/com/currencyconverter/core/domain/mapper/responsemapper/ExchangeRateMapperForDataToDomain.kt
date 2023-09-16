package com.currencyconverter.core.domain.mapper.responsemapper

import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.data.utils.currencyToCountryMap
import com.currencyconverter.core.domain.mapper.MapperDataToDomain
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.shared.numberFormat

class ExchangeRateMapperForDataToDomain(var source: String? = null, var amount: Double = 0.0) :
    MapperDataToDomain<List<ExchangeRateModel>, List<ExchangeResultUiModel>> {

    override fun invoke(data: List<ExchangeRateModel>): List<ExchangeResultUiModel> {
        if (source != null && amount != 0.0) {
            return data.firstOrNull { it.code == source }?.let { sourceCurrency ->
                val rateSource = sourceCurrency.rate
                // convert from source to base
                val inBase = amount / rateSource
                data.map {
                    // convert from base to all currencies
                    val rateResult = it.rate * inBase
                    ExchangeResultUiModel(
                        code = "${it.code} ${it.code.toFlagEmoji()}",
                        amount = numberFormat.format(rateResult)
                    )
                }
            } ?: emptyList()
        } else {
            return emptyList()
        }
    }
}

fun String.toFlagEmoji(): String {
    val countryCode = currencyToCountryMap[this.uppercase()].toString()
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6

    // Return the flag Unicode symbol
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}