package com.currencyconverter.core.data.repository

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.date.TimeHelper
import com.currencyconverter.core.data.mapper.exchangerate.ExchangeRateMapperForEntityToDataModel
import com.currencyconverter.core.data.mapper.exchangerate.ExchangeRateMapperForResponseToEntity
import com.currencyconverter.core.database.datasource.ExchangeRateLocalDataSource
import com.currencyconverter.core.remote.datasource.ExchangeRateRemoteDataSource
import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse
import com.currencyconverter.core.testing.datastore.FakeCurrencyConverterAppDataStore
import com.currencyconverter.core.testing.fakedata.baseCurrency
import com.currencyconverter.core.testing.fakedata.exchangeRateCurrencyResponse
import com.currencyconverter.core.testing.fakedata.getListOfExchangeRatesEntities
import com.currencyconverter.core.testing.fakedata.getListOfExchangeRatesModels
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRatesRetrieveRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeCurrencyConverterAppDataStore: FakeCurrencyConverterAppDataStore
    private val exchangeRateMapperForResponseToEntity = ExchangeRateMapperForResponseToEntity()
    private val exchangeRateMapperForEntityToDataModel = ExchangeRateMapperForEntityToDataModel()

    private val exchangeRateRemoteDataSource = mockk<ExchangeRateRemoteDataSource>()
    private val exchangeRateLocalDataSource = mockk<ExchangeRateLocalDataSource>()

    private val timeHelper = mockk<TimeHelper>()

    private lateinit var currencyRepository: ExchangeRatesRetrieveRepository

    @Before
    fun setup() {
        fakeCurrencyConverterAppDataStore = FakeCurrencyConverterAppDataStore()
        currencyRepository = ExchangeRatesRetrieveRepositoryImpl(
            exchangeRateRemoteDataSource = exchangeRateRemoteDataSource,
            exchangeRateLocalDataSource = exchangeRateLocalDataSource,
            currencyConverterAppDataStore = fakeCurrencyConverterAppDataStore,
            exchangeRateMapperForResponseToEntity = exchangeRateMapperForResponseToEntity,
            exchangeRateMapperForEntityToDataModel = exchangeRateMapperForEntityToDataModel,
            timeHelper = timeHelper
        )
    }

    @Test
    fun `getLatestExchangeRate should call remote and store in local`() =
        mainCoroutineRule.runTest {

            val expectedLastUpdateTime = 1_662_132_910_854
            val remoteExchangeRateEntities = getListOfExchangeRatesEntities
            val remoteExchangeRateModels = getListOfExchangeRatesModels

            coEvery { exchangeRateRemoteDataSource.getLatestExchangeRate() } returns exchangeRateCurrencyResponse
            coEvery { exchangeRateLocalDataSource.getAllExchangeRatesByBase(baseCurrency) } returns remoteExchangeRateEntities
            coEvery { exchangeRateLocalDataSource.saveExchangeRates(any()) } returns Unit
            coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

            val result = currencyRepository.getExchangeRatesByBase().toList().first()

            when (result) {
                is ResponseDataStateHandler.Success -> {
                    assertThat(result.data.size).isEqualTo(remoteExchangeRateEntities.size)
                }

                else -> {}
            }

            assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForExchangeRateList())
            assertThat(baseCurrency).isEqualTo(fakeCurrencyConverterAppDataStore.latestBase.first())
            assertThat(result).isEqualTo(ResponseDataStateHandler.Success(remoteExchangeRateModels))

            coVerify(exactly = 1) {
                exchangeRateLocalDataSource.saveExchangeRates(
                    remoteExchangeRateEntities
                )
            }
        }

    @Test
    fun `getLatestExchangeRate should call only from local data if refresh data limit not finished`() =
        mainCoroutineRule.runTest {

            val expectedLastUpdateTime = 1_662_130_320_854
            val remoteExchangeRateEntities = getListOfExchangeRatesEntities
            val remoteExchangeRateModels = getListOfExchangeRatesModels

            coEvery { exchangeRateRemoteDataSource.getLatestExchangeRate() } returns exchangeRateCurrencyResponse
            coEvery { exchangeRateLocalDataSource.getAllExchangeRatesByBase(baseCurrency) } returns remoteExchangeRateEntities
            coEvery { exchangeRateLocalDataSource.saveExchangeRates(any()) } returns Unit
            coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

            val result = currencyRepository.getExchangeRatesByBase().toList().first()

            assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForExchangeRateList())
            assertThat(baseCurrency).isEqualTo(fakeCurrencyConverterAppDataStore.latestBase.first())
            assertThat(result).isEqualTo(ResponseDataStateHandler.Success(remoteExchangeRateModels))
        }


    @Test
    fun `getLatestExchangeRate should return Empty when no data is available locally`() =
        mainCoroutineRule.runTest {

            val expectedLastUpdateTime = 1_662_130_320_854
            val exchangeRateCurrencyResponse =
                ExchangeRateCurrencyResponse(base = baseCurrency, rates = emptyMap())

            coEvery { exchangeRateRemoteDataSource.getLatestExchangeRate() } returns exchangeRateCurrencyResponse
            coEvery { exchangeRateLocalDataSource.getAllExchangeRatesByBase(baseCurrency) } returns emptyList()
            coEvery { exchangeRateLocalDataSource.saveExchangeRates(any()) } returns Unit

            coEvery { timeHelper.currentTimeMillis } returns expectedLastUpdateTime

            val result = currencyRepository.getExchangeRatesByBase().toList().first()

            assertThat(expectedLastUpdateTime).isEqualTo(fakeCurrencyConverterAppDataStore.getLastUpdateTimeForExchangeRateList())
            assertThat(baseCurrency).isEqualTo(fakeCurrencyConverterAppDataStore.latestBase.first())
            assertThat(result).isEqualTo(ResponseDataStateHandler.Empty)
        }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}

