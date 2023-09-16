package com.currencyconverter.core.database.datasource

import com.currencyconverter.core.database.dao.ExchangeRatesDao
import com.currencyconverter.core.database.entity.ExchangeRatesEntity
import javax.inject.Inject

interface ExchangeRateLocalDataSource {

    fun getAllExchangeRatesByBase(base: String): List<ExchangeRatesEntity>

    fun saveExchangeRates(exchangeRateList: List<ExchangeRatesEntity>)
}

class ExchangeRateLocalDataSourceImpl @Inject constructor(
    private val exchangeRatesDao: ExchangeRatesDao
) : ExchangeRateLocalDataSource {

    override fun getAllExchangeRatesByBase(base: String): List<ExchangeRatesEntity> {
        return exchangeRatesDao.getAllByBase(base)
    }

    override fun saveExchangeRates(exchangeRateList: List<ExchangeRatesEntity>) {
        return exchangeRatesDao.insertAll(exchangeRateList)
    }
}
