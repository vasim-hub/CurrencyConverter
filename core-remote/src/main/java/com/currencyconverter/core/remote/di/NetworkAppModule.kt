package com.currencyconverter.core.remote.di

import com.currencyconverter.core.remote.BuildConfig
import com.currencyconverter.core.remote.customexception.ApiErrorBody
import com.currencyconverter.core.remote.datasource.CurrencyRemoteDataSource
import com.currencyconverter.core.remote.datasource.CurrencyRemoteDataSourceImpl
import com.currencyconverter.core.remote.datasource.ExchangeRateRemoteDataSource
import com.currencyconverter.core.remote.datasource.ExchangeRateRemoteDataSourceImpl
import com.currencyconverter.core.remote.services.CurrencyConverterApiService
import com.currencyconverter.core.remote.utils.TOKEN_PREFIX
import com.currencyconverter.core.remote.utils.ext.addLoggingInterceptor
import com.currencyconverter.core.remote.utils.ext.apiHandleErrors
import com.currencyconverter.core.remote.utils.ext.setHeaders
import com.currencyconverter.core.remote.utils.ext.setOpenExchangeApiKey
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkAppModule {

    @Binds
    fun provideCurrencyRemoteDataSource(dataSource: CurrencyRemoteDataSourceImpl): CurrencyRemoteDataSource

    @Binds
    fun provideExchangeRateRemoteDataSource(exchangeRateRemoteDataSource: ExchangeRateRemoteDataSourceImpl): ExchangeRateRemoteDataSource

    companion object {

        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        @Singleton
        @Provides
        fun provideApiErrorBodyAdapter(): JsonAdapter<ApiErrorBody> {
            return moshi.adapter(ApiErrorBody::class.java)
        }


        @Singleton
        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL).client(okHttpClient).build()


        @Singleton
        @Provides
        fun provideOkhttp(jsonAdapter: JsonAdapter<ApiErrorBody>): OkHttpClient {
            return OkHttpClient.Builder().addLoggingInterceptor().setHeaders {
                    setOpenExchangeApiKey(TOKEN_PREFIX)
                }.apiHandleErrors(jsonAdapter).callTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).build()
        }

        @Singleton
        @Provides
        fun provideCurrencyConverterApiService(retrofit: Retrofit): CurrencyConverterApiService =
            retrofit.create(CurrencyConverterApiService::class.java)

        private const val TIMEOUT_IN_SECONDS = 30L
    }
}

