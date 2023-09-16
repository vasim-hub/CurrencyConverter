package com.currencyconverter.core.data.repository

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.date.TimeHelper
import com.currencyconverter.core.data.mapper.currencylist.CurrencyMapperForEntityToDataModel
import com.currencyconverter.core.data.mapper.currencylist.CurrencyMapperForResponseToEntity
import com.currencyconverter.core.database.datasource.CurrencyLocalDataSource
import com.currencyconverter.core.remote.datasource.CurrencyRemoteDataSource
import com.currencyconverter.core.testing.datastore.FakeCurrencyConverterAppDataStore
import com.currencyconverter.core.testing.fakedata.currencyMap
import com.currencyconverter.core.testing.fakedata.getListOfCurrencyEntities
import com.currencyconverter.core.testing.fakedata.getListOfCurrencyModels
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeCurrencyConverterAppDataStore: FakeCurrencyConverterAppDataStore
    private val currencyMapperForResponseToEntity = CurrencyMapperForResponseToEntity()
    private val currencyMapperForEntityToDataModel = CurrencyMapperForEntityToDataModel()

    private val currencyRemoteDataSource = mockk<CurrencyRemoteDataSource>()
    private val currencyLocalDataSource = mockk<CurrencyLocalDataSource>()

    private val timeHelper = mockk<TimeHelper>()

    private lateinit var currencyRepository: CurrencyRepository

    @Before
    fun setup() {
        fakeCurrencyConverterAppDataStore = FakeCurrencyConverterAppDataStore()
        currencyRepository =
            CurrencyRepositoryImpl(
                currencyRemoteDataSource = currencyRemoteDataSource,
                currencyLocalDataSource = currencyLocalDataSource,
                currencyConverterAppDataStore = fakeCurrencyConverterAppDataStore,
                currencyMapperForResponseToEntity = currencyMapperForResponseToEntity,
                currencyMapperForEntityToDataModel = currencyMapperForEntityToDataModel,
                timeHelper = timeHelper
            )
    }

    @Test
    fun `getCurrencyList should call remote and store in local`() = mainCoroutineRule.runTest {

        val expectedLastUpdateTime = 1_662_132_910_854
        val remoteData = currencyMap
        val remoteCurrencyEntity = getListOfCurrencyEntities
        val remoteCurrencyModel = getListOfCurrencyModels

        coEvery { currencyRemoteDataSource.getCurrencies() } returns remoteData
        coEvery { currencyLocalDataSource.getAllCurrencies() } returns remoteCurrencyEntity
        coEvery { currencyLocalDataSource.saveCurrencies(any()) } returns Unit
        coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

        val result = currencyRepository.getCurrencyList().toList().first()

        assertThat(result).isEqualTo(ResponseDataStateHandler.Success(remoteCurrencyModel))
        assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForCurrencyList())
        coVerify(exactly = 1) { currencyLocalDataSource.saveCurrencies(remoteCurrencyEntity) }
    }

    @Test
    fun `getCurrencyList should call only from local data if refresh data limit not finished`() =
        mainCoroutineRule.runTest {

            val expectedLastUpdateTime = 1_662_130_320_854
            val remoteData = currencyMap
            val remoteCurrencyEntity = getListOfCurrencyEntities
            val remoteCurrencyModel = getListOfCurrencyModels

            coEvery { currencyRemoteDataSource.getCurrencies() } returns remoteData
            coEvery { currencyLocalDataSource.getAllCurrencies() } returns remoteCurrencyEntity
            coEvery { currencyLocalDataSource.saveCurrencies(any()) } returns Unit
            coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

            val result = currencyRepository.getCurrencyList().toList().first()

            assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForCurrencyList())
            assertThat(result).isEqualTo(ResponseDataStateHandler.Success(remoteCurrencyModel))
        }


    @Test
    fun `getCurrencyList should return Empty when no data is available locally`() =
        mainCoroutineRule.runTest {

            val expectedLastUpdateTime = 1_662_130_320_854

            coEvery { currencyRemoteDataSource.getCurrencies() } returns mapOf()
            coEvery { currencyLocalDataSource.getAllCurrencies() } returns emptyList()
            coEvery { currencyLocalDataSource.saveCurrencies(any()) } returns Unit
            coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

            val result = currencyRepository.getCurrencyList().toList().first()

            assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForCurrencyList())
            assertThat(result).isEqualTo(ResponseDataStateHandler.Empty)
        }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}

