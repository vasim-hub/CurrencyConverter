package com.currencyconverter.core.testing.fakedata

import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.database.entity.CurrencyEntity

val currencyMap = mapOf(
    "AED" to "United Arab Emirates Dirham",
    "AFN" to "Afghan Afghani",
    "ALL" to "Albanian Lek",
    "AMD" to "Armenian Dram"
)

val getListOfCurrencyEntities = currencyMap.entries.map { (code, name) ->
    CurrencyEntity(code, name)
}


val getListOfCurrencyModels = currencyMap.entries.map { (code, name) ->
    CurrencyModel(code, name)
}