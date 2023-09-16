package com.currencyconverter.core.data.di

import com.currencyconverter.core.data.date.TimeHelper
import com.currencyconverter.core.data.date.TimeHelperImpl
import com.currencyconverter.core.data.repository.CurrencyRepository
import com.currencyconverter.core.data.repository.CurrencyRepositoryImpl
import com.currencyconverter.core.data.repository.ExchangeRatesRetrieveRepository
import com.currencyconverter.core.data.repository.ExchangeRatesRetrieveRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsExchangeRatesRetrieveRepository(
        exchangeRatesRetrieveRepository: ExchangeRatesRetrieveRepositoryImpl
    ): ExchangeRatesRetrieveRepository

    @Singleton
    @Binds
    fun bindsCurrencyRepository(
        currencyRepository: CurrencyRepositoryImpl
    ): CurrencyRepository


    @Singleton
    @Binds
    fun bindsTimeHelper(timeHelper: TimeHelperImpl): TimeHelper
}
