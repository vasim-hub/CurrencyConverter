package com.currencyconverter.core.database.di

import com.currencyconverter.core.database.datasource.CurrencyLocalDataSource
import com.currencyconverter.core.database.datasource.CurrencyLocalDataSourceImpl
import com.currencyconverter.core.database.datasource.ExchangeRateLocalDataSource
import com.currencyconverter.core.database.datasource.ExchangeRateLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsCurrencyLocalDataSource(
        currencyLocalDataSource: CurrencyLocalDataSourceImpl
    ): CurrencyLocalDataSource

    @Binds
    fun bindsExchangeRateLocalDataSource(
        exchangeRateLocalDataSource: ExchangeRateLocalDataSourceImpl
    ): ExchangeRateLocalDataSource

}
