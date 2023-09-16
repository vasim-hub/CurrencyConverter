package com.currencyconverter.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.currencyconverter.core.database.AppDatabase
import com.currencyconverter.core.database.entity.CurrencyEntity
import com.currencyconverter.core.testing.fakedata.getListOfCurrencyEntities
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyDaoTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var currencyDao: CurrencyDao

    private var currencyList = emptyList<CurrencyEntity>()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        currencyDao = appDatabase.currencyDao()

        insertRequiredData()
    }

    private fun insertRequiredData() {
        currencyList = getListOfCurrencyEntities
        currencyDao.insertAll(currencyList)
    }

    @Test
    fun testGetCurrencyByCodeFound() = mainCoroutineRule.runTest {
        val currencyEntity = currencyDao.getCurrencyByCode("JPY")
        Truth.assertThat(currencyEntity).isNotNull()
    }

    @Test
    fun testGetCurrencyRecordsEqualToMapData() = mainCoroutineRule.runTest {
        val listOfCurrencyEntities = currencyDao.getAll()
        Truth.assertThat(listOfCurrencyEntities.size).isEqualTo(currencyList.size)
    }

    @Test
    fun testGetCurrencyByCodeNotFound() = mainCoroutineRule.runTest {
        val currencyEntity = currencyDao.getCurrencyByCode("ABC")
        Truth.assertThat(currencyEntity).isNull()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}