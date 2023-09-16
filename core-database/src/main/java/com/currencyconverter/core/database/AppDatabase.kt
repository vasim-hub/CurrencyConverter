

package com.currencyconverter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currencyconverter.core.database.converters.DateConverter
import com.currencyconverter.core.database.dao.CurrencyDao
import com.currencyconverter.core.database.dao.ExchangeRatesDao
import com.currencyconverter.core.database.entity.CurrencyEntity
import com.currencyconverter.core.database.entity.ExchangeRatesEntity

@Database(
    exportSchema = true,
    entities = [
        CurrencyEntity::class,
        ExchangeRatesEntity::class,
    ],
    version = 1
)

@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRatesDao(): ExchangeRatesDao
}
