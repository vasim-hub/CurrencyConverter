package com.currencyconverter.core.remote.datasource

import com.currencyconverter.core.remote.services.CurrencyConverterApiService
import com.currencyconverter.core.testing.fakedata.currencyMap
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRemoteDataSourceImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `getCurrencies should return a map of currencies`() = mainCoroutineRule.runTest {
        // Given

        // Create a mock instance of CurrencyConverterApiService using MockK
        val currencyConverterApiService: CurrencyConverterApiService = mockk()

        // Stub the behavior of CurrencyConverterApiService.getCurrencies() using MockK
        coEvery { currencyConverterApiService.getCurrencies() } returns currencyMap

        // Create an instance of CurrencyRemoteDataSourceImpl using the mocked CurrencyConverterApiService
        val currencyRemoteDataSource: CurrencyRemoteDataSource =
            CurrencyRemoteDataSourceImpl(currencyConverterApiService)

        // When
        val result = currencyRemoteDataSource.getCurrencies()

        // Then
        // Use Truth's assertThat to perform the assertion
        assertThat(result).isEqualTo(currencyMap)
    }


    @Test
    fun `getCurrencies should return an empty map if the API response is empty`() =
        mainCoroutineRule.runTest {
            // Given
            val emptyResponse = emptyMap<String, String>()

            // Create a mock instance of CurrencyConverterApiService using MockK
            val currencyConverterApiService: CurrencyConverterApiService = mockk()

            // Stub the behavior of CurrencyConverterApiService.getCurrencies() using MockK
            coEvery { currencyConverterApiService.getCurrencies() } returns emptyResponse

            // Create an instance of CurrencyRemoteDataSourceImpl using the mocked CurrencyConverterApiService
            val currencyRemoteDataSource: CurrencyRemoteDataSource =
                CurrencyRemoteDataSourceImpl(currencyConverterApiService)

            // When
            val result = currencyRemoteDataSource.getCurrencies()

            // Then
            // Use Truth's assertThat to perform the assertion
            assertThat(result).isEmpty()
        }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}
