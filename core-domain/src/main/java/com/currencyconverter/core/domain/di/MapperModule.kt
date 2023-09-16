package com.currencyconverter.core.domain.di

import com.currencyconverter.core.domain.mapper.responsemapper.ExchangeRateMapperForDataToDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Singleton
    @Provides
    fun provideExchangeRateMapperForDataToDomain(): ExchangeRateMapperForDataToDomain {
        return ExchangeRateMapperForDataToDomain()
    }
}