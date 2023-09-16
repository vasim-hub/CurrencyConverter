package com.currencyconverter.core.database.datasource

import com.currencyconverter.core.database.dao.CurrencyDao
import com.currencyconverter.core.database.entity.CurrencyEntity
import javax.inject.Inject

interface CurrencyLocalDataSource {

    fun getAllCurrencies(): List<CurrencyEntity>

    suspend fun getCurrency(code: String): CurrencyEntity?

    fun saveCurrencies(currencies: List<CurrencyEntity>)
}

class CurrencyLocalDataSourceImpl @Inject constructor(
    private val currencyDao: CurrencyDao
) : CurrencyLocalDataSource {

    override fun getAllCurrencies(): List<CurrencyEntity> {
        return currencyDao.getAll()
    }

    override suspend fun getCurrency(code: String): CurrencyEntity? {
        return currencyDao.getCurrencyByCode(code)
    }

    override fun saveCurrencies(currencies: List<CurrencyEntity>) {
        return currencyDao.insertAll(currencies)
    }
}
