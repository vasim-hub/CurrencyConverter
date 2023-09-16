package com.currencyconverter.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.currencyconverter.core.database.base.AbstractBaseEntityDao
import com.currencyconverter.core.database.entity.CurrencyEntity

@Dao
abstract class CurrencyDao : AbstractBaseEntityDao<CurrencyEntity>() {

    @Query("SELECT * FROM currency order by code asc")
    abstract fun getAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE code = :code")
    abstract suspend fun getCurrencyByCode(code: String): CurrencyEntity?
}