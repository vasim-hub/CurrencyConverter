package com.currencyconverter.core.database.entity

import androidx.room.Entity
import com.currencyconverter.core.database.base.BaseEntity
import com.currencyconverter.core.database.utils.TableConstantsName

@Entity(tableName = TableConstantsName.EXCHANGE_RATES, primaryKeys = ["code"])
data class ExchangeRatesEntity(
    val code: String,
    val base: String,
    val rate: Double
) : BaseEntity()
