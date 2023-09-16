package com.currencyconverter.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currencyconverter.core.database.base.BaseEntity
import com.currencyconverter.core.database.utils.TableConstantsName

@Entity(tableName = TableConstantsName.CURRENCIES)
data class CurrencyEntity(
    @PrimaryKey val code: String,
    val name: String
) : BaseEntity()





