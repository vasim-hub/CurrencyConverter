package com.currencyconverter.core.remote.datasource

import com.currencyconverter.core.remote.services.CurrencyConverterApiService
import com.currencyconverter.core.testing.fakedata.exchangeRateCurrencyResponse
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRateRemoteDataSourceImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `test getLatestExchangeRate success`() = mainCoroutineRule.runTest {
        // Given
        val currencyConverterApiService = mockk<CurrencyConverterApiService>()
        val dataSource: ExchangeRateRemoteDataSource =
            ExchangeRateRemoteDataSourceImpl(currencyConverterApiService)

        val response = exchangeRateCurrencyResponse

        coEvery { currencyConverterApiService.getCurrentExchangeRate() } returns response

        // When
        val result = dataSource.getLatestExchangeRate()

        // Then
        assertThat(result).isEqualTo(response)
    }


    @Test(expected = IOException::class)
    fun `test getLatestExchangeRate network error`() = mainCoroutineRule.runTest {
        // Given
        val currencyConverterApiService = mockk<CurrencyConverterApiService>()
        val dataSource: ExchangeRateRemoteDataSource =
            ExchangeRateRemoteDataSourceImpl(currencyConverterApiService)

        coEvery { currencyConverterApiService.getCurrentExchangeRate() } throws IOException("Network error")

        // When
        dataSource.getLatestExchangeRate()

        // The test is expected to throw an IOException, so no need for assertions
    }

    @Test(expected = Exception::class)
    fun `test getLatestExchangeRate server error`() = mainCoroutineRule.runTest {
        // Given
        val currencyConverterApiService = mockk<CurrencyConverterApiService>()
        val dataSource: ExchangeRateRemoteDataSource =
            ExchangeRateRemoteDataSourceImpl(currencyConverterApiService)

        coEvery { currencyConverterApiService.getCurrentExchangeRate() } throws Exception("Server error")

        // When
        dataSource.getLatestExchangeRate()

        // The test is expected to throw an exception, so no need for assertions
    }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}
