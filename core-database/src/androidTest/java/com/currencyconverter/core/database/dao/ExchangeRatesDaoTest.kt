package com.currencyconverter.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.currencyconverter.core.database.AppDatabase
import com.currencyconverter.core.database.entity.ExchangeRatesEntity
import com.currencyconverter.core.testing.fakedata.exchangeRateDataMap
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRatesDaoTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var exchangeRatesDao: ExchangeRatesDao

    private var exchangeRateList = emptyList<ExchangeRatesEntity>()


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        exchangeRatesDao = appDatabase.exchangeRatesDao()

        insertRequiredData()
    }

    private fun insertRequiredData() {
        exchangeRateList = exchangeRateDataMap.entries.map { (code, rate) ->
            ExchangeRatesEntity(
                code = code, base = "USD", rate = rate
            )
        }
        exchangeRatesDao.insertAll(exchangeRateList)
    }

    @Test
    fun testGetExchangeRatesByBaseFound() = mainCoroutineRule.runTest {
        val listOfExchangeRatesEntities = exchangeRatesDao.getAllByBase("USD")
        Truth.assertThat(listOfExchangeRatesEntities).isNotEmpty()
    }

    @Test
    fun testGetExchangeRatesByBaseNotFound() = mainCoroutineRule.runTest {
        val listOfExchangeRatesEntities = exchangeRatesDao.getAllByBase("ABC")
        Truth.assertThat(listOfExchangeRatesEntities).isEmpty()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}