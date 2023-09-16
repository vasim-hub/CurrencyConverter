package com.currencyconverter.core.testing.datastore

import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStore
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeCurrencyConverterAppDataStore : CurrencyConverterAppDataStore {

    private var lastUpdateFlowCurrencyList: Long? = null
    private var lastUpdateFlowExchangeRateList: Long? = null

    private val latestBaseFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun getLastUpdateTimeForCurrencyList(): Long? {
        return lastUpdateFlowCurrencyList
    }

    override suspend fun getLastUpdateTimeForExchangeRateList(): Long? {
        return lastUpdateFlowExchangeRateList
    }

    override val latestBase: Flow<String>
        get() = latestBaseFlow

    override suspend fun setLastUpdateTimeForCurrencyList(lastUpdate: Long) {
        lastUpdateFlowCurrencyList = lastUpdate
    }

    override suspend fun setLastUpdateTimeForExchangeRateList(lastUpdate: Long) {
        lastUpdateFlowExchangeRateList = lastUpdate
    }

    override suspend fun setLatestBase(base: String) {
        latestBaseFlow.emit(base)
    }
}
