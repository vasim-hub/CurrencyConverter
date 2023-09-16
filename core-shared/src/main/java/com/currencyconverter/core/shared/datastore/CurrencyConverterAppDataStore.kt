package com.currencyconverter.core.shared.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.currencyconverter.core.shared.datastore.DataStoreKeys.LAST_UPDATE_CURRENCY_LIST_KEY
import com.currencyconverter.core.shared.datastore.DataStoreKeys.LAST_UPDATE_EXCHANGE_RATE_KEY
import com.currencyconverter.core.shared.datastore.DataStoreKeys.LATEST_BASE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface CurrencyConverterAppDataStore {

    suspend fun getLastUpdateTimeForCurrencyList(): Long?

    suspend fun getLastUpdateTimeForExchangeRateList(): Long?

    val latestBase: Flow<String>

    suspend fun setLastUpdateTimeForCurrencyList(lastUpdate: Long)

    suspend fun setLastUpdateTimeForExchangeRateList(lastUpdate: Long)

    suspend fun setLatestBase(base: String)
}

class CurrencyConverterAppDataStoreImpl(private val appContext: Context) :
    CurrencyConverterAppDataStore {

    private val Context.dataStore by preferencesDataStore("currency_converter_data_store")


    override suspend fun getLastUpdateTimeForCurrencyList(): Long? {
        return appContext.dataStore.data.map { preferences ->
            preferences[LAST_UPDATE_CURRENCY_LIST_KEY]
        }.firstOrNull()
    }

    override suspend fun getLastUpdateTimeForExchangeRateList(): Long? {
        return appContext.dataStore.data.map { preferences ->
            preferences[LAST_UPDATE_EXCHANGE_RATE_KEY]
        }.firstOrNull()
    }

    override val latestBase: Flow<String> = appContext.dataStore.data.map { preferences ->
        preferences[LATEST_BASE_KEY] ?: "USD"
    }

    override suspend fun setLastUpdateTimeForCurrencyList(lastUpdate: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[LAST_UPDATE_CURRENCY_LIST_KEY] = lastUpdate
        }
    }

    override suspend fun setLastUpdateTimeForExchangeRateList(lastUpdate: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[LAST_UPDATE_EXCHANGE_RATE_KEY] = lastUpdate
        }
    }

    override suspend fun setLatestBase(base: String) {
        appContext.dataStore.edit { preferences ->
            preferences[LATEST_BASE_KEY] = base
        }
    }
}

object DataStoreKeys {
    val LAST_UPDATE_CURRENCY_LIST_KEY = longPreferencesKey("last_update_currency_list")
    val LAST_UPDATE_EXCHANGE_RATE_KEY = longPreferencesKey("last_update_rate_list")
    val LATEST_BASE_KEY = stringPreferencesKey("latest_base_exchange")
}


