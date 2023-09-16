package com.currencyconverter.core.shared.di

import android.content.Context
import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStore
import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedModule {

    companion object {

        @Singleton
        @Provides
        fun provideCurrencyConverterAppDataStoreImpl(@ApplicationContext appContext: Context): CurrencyConverterAppDataStore {
            return CurrencyConverterAppDataStoreImpl(appContext)
        }
    }
}
