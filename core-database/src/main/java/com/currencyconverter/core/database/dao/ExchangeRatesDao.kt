package com.currencyconverter.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.currencyconverter.core.database.base.AbstractBaseEntityDao
import com.currencyconverter.core.database.entity.ExchangeRatesEntity

@Dao
abstract class ExchangeRatesDao : AbstractBaseEntityDao<ExchangeRatesEntity>() {

    @Query("SELECT * FROM exchange_rate WHERE base = :base ORDER BY code asc")
    abstract fun getAllByBase(base: String): List<ExchangeRatesEntity>

}
