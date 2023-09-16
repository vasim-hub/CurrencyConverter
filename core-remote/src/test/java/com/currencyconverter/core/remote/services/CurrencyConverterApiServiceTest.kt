package com.currencyconverter.core.remote.services

import com.currencyconverter.core.remote.customexception.APIErrorException
import com.currencyconverter.core.remote.customexception.ApiErrorBody
import com.currencyconverter.core.remote.model.ExchangeRateCurrencyResponse
import com.currencyconverter.core.remote.utils.TOKEN_PREFIX
import com.currencyconverter.core.remote.utils.ext.apiHandleErrors
import com.currencyconverter.core.remote.utils.ext.setHeaders
import com.currencyconverter.core.remote.utils.ext.setOpenExchangeApiKey
import com.currencyconverter.core.testing.fakedata.currencyMap
import com.currencyconverter.core.testing.fakedata.fakejson.fakeJSONResponseExchangeRateForSuccess
import com.currencyconverter.core.testing.fakedata.fakejson.fakeJsonResponseExchangeRateForError
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyConverterApiServiceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: CurrencyConverterApiService
    private lateinit var jsonAdapter: JsonAdapter<ApiErrorBody>
    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        // Start the MockWebServer before each test
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Initialize Moshi to convert JSON responses
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        jsonAdapter = moshi.adapter(ApiErrorBody::class.java)

        // Create the OkHttpClient with the apiHandleErrors interceptor
        val okHttpClient = OkHttpClient.Builder().apiHandleErrors(jsonAdapter).build()

        // Create the Retrofit client pointing to the MockWebServer
        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url("/")).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

        // Create the CurrencyConverterApiService
        apiService = retrofit.create(CurrencyConverterApiService::class.java)
    }

    @Test
    fun `getCurrencies should return the API Success`() = mainCoroutineRule.runTest {
        // Given
        val mapType =
            Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val mapAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(mapType)
        val responseBody = mapAdapter.toJson(currencyMap)

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(responseBody))

        // When
        val response = apiService.getCurrencies()

        for ((key, value) in response) {
            Truth.assertThat(currencyMap.containsKey(key)).isTrue()
            Truth.assertThat(currencyMap[key]).isEqualTo(value)
        }
    }

    @Test
    fun `getCurrentExchangeRate should return the expected ExchangeRateCurrencyResponse`() =
        mainCoroutineRule.runTest {

            // Create the OkHttpClient with the apiHandleErrors interceptor
            val okHttpClient = OkHttpClient.Builder().apiHandleErrors(jsonAdapter).setHeaders {
                    setOpenExchangeApiKey(TOKEN_PREFIX)
                }.build()

            // Create the Retrofit client pointing to the MockWebServer
            val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url("/")).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

            // Create the CurrencyConverterApiService
            apiService = retrofit.create(CurrencyConverterApiService::class.java)

            // Given
            mockWebServer.enqueue(
                MockResponse().setResponseCode(200).setBody(
                    fakeJSONResponseExchangeRateForSuccess
                )
            )

            val mapType = Types.newParameterizedType(
                ExchangeRateCurrencyResponse::class.java, ExchangeRateCurrencyResponse::class.java
            )
            val mapAdapter: JsonAdapter<ExchangeRateCurrencyResponse?> = moshi.adapter(mapType)

            val expectedExchangeRateCurrencyResponse: ExchangeRateCurrencyResponse =
                mapAdapter.fromJson(
                    fakeJSONResponseExchangeRateForSuccess
                )!!

            // When
            val actualExchangeRateCurrencyResponse = apiService.getCurrentExchangeRate()

            // Then
            Truth.assertThat(actualExchangeRateCurrencyResponse.rates.size)
                .isEqualTo(expectedExchangeRateCurrencyResponse.rates.size)

            for (key in actualExchangeRateCurrencyResponse.rates.keys) {
                Truth.assertThat(actualExchangeRateCurrencyResponse.rates[key])
                    .isEqualTo(expectedExchangeRateCurrencyResponse.rates[key])
            }

        }

    @Test
    fun `getCurrentExchangeRate should throw APIErrorException and check API error message`() =
        mainCoroutineRule.runTest {
            // Given
            mockWebServer.enqueue(
                MockResponse().setResponseCode(403).setBody(
                    fakeJsonResponseExchangeRateForError
                )
            )

            val apiErrorBody: ApiErrorBody? = jsonAdapter.fromJson(
                fakeJsonResponseExchangeRateForError
            )
            val errorDescriptionMessage = apiErrorBody?.description ?: ""

            // When
            try {
                apiService.getCurrentExchangeRate()

            } catch (e: APIErrorException) {
                // Then
                Truth.assertThat(errorDescriptionMessage).isEqualTo(e.message)
            }
        }

    @After
    fun tearDown() {
        // Stop the MockWebServer after each test
        mockWebServer.shutdown()
    }
}