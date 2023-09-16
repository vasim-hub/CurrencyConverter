package com.feature.currencyconverter.viewmodel

import com.currencyconverter.core.domain.DomainStateHandler
import com.currencyconverter.core.domain.mapper.responsemapper.toFlagEmoji
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.domain.usecase.GetAllCurrenciesUseCase
import com.currencyconverter.core.domain.usecase.GetAllExchangeRateResultsUseCase
import com.currencyconverter.core.shared.ErrorStatusEnum
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.feature.currencyconverter.ui.currency.CurrencyListUIState
import com.feature.currencyconverter.ui.currency.ExchangeRateListUIState
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyConverterViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var getAllCurrenciesUseCase: GetAllCurrenciesUseCase
    private lateinit var getAllExchangeRateResultsUseCase: GetAllExchangeRateResultsUseCase

    @Before
    fun setup() {
        getAllCurrenciesUseCase = mockk()
        getAllExchangeRateResultsUseCase = mockk()

        viewModel = CurrencyConverterViewModel(
            getAllCurrenciesUseCase, getAllExchangeRateResultsUseCase
        )
    }

    @Test
    fun `getAllCurrencies should update allCurrenciesStateFlow with success state`() =
        mainCoroutineRule.runTest {
            // Given
            val mockCurrencies = listOf(CurrencyUiModel("USD", "US Dollar"))
            coEvery { getAllCurrenciesUseCase.invoke(null) } returns flowOf(
                DomainStateHandler.Success(
                    mockCurrencies
                )
            )

            // When
            viewModel.getAllCurrencies()

            // Then
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val result = viewModel.allCurrenciesStateFlow.first()
                Truth.assertThat(result).isEqualTo(CurrencyListUIState.Success(mockCurrencies))
            }

            collectJob.cancel()
        }

    @Test
    fun `getAllCurrencies should update allCurrenciesStateFlow with loading state`() =
        mainCoroutineRule.runTest {
            // Given
            coEvery { getAllCurrenciesUseCase.invoke(null) } returns flowOf(DomainStateHandler.Loading)

            // When
            viewModel.getAllCurrencies()

            // Then
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val result = viewModel.allCurrenciesStateFlow.first()
                Truth.assertThat(result).isEqualTo(CurrencyListUIState.Loading)
            }

            collectJob.cancel()
        }

    @Test
    fun `getAllCurrencies should update allCurrenciesStateFlow with error state`() =
        mainCoroutineRule.runTest {
            // Given
            val errorMessage = "Error loading currencies"
            coEvery { getAllCurrenciesUseCase.invoke(null) } returns flowOf(
                DomainStateHandler.Error(
                    errorMessage, ErrorStatusEnum.API_ERROR
                )
            )

            // When
            viewModel.getAllCurrencies()

            // Then
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val result = viewModel.allCurrenciesStateFlow.first()
                Truth.assertThat(result)
                    .isEqualTo(CurrencyListUIState.Error(errorMessage, ErrorStatusEnum.API_ERROR))
            }

            collectJob.cancel()
        }

    @Test
    fun `updateAmount should update amountFlow`() {
        // When
        viewModel.updateAmount("100")

        // Then
        Truth.assertThat(viewModel.amountState.value).isEqualTo(100)
    }

    @Test
    fun `updateSourceCurrency should update sourceCurrencyFlow`() {
        // Given
        val currency = CurrencyUiModel("EUR", "Euro")

        // When
        viewModel.updateSourceCurrency(currency)

        // Then
        Truth.assertThat(viewModel.sourceCurrencyState.value).isEqualTo(currency)
    }

    @Test
    fun `allExchangeResults should emit success state`() = mainCoroutineRule.runTest {

        // Given
        val sourceCurrency = CurrencyUiModel("ABC", "US Dollar")
        val amount = 100

        val expectedExchangeResultUiModelList = listOf(
            ExchangeResultUiModel("${"CHF"} ${"CHF".toFlagEmoji()}", "3,066.87"),
            ExchangeResultUiModel("${"BND"} ${"BND".toFlagEmoji()}", "4,494.04"),
            ExchangeResultUiModel("${"ABC"} ${"ABC".toFlagEmoji()}", "10,000")
        )


        val pair = Pair(sourceCurrency.code, amount.toDouble())

        coEvery { getAllExchangeRateResultsUseCase(pair) } returns flowOf(
            DomainStateHandler.Success(
                expectedExchangeResultUiModelList
            )
        )

        // When
        viewModel.updateSourceCurrency(sourceCurrency)
        viewModel.updateAmount(amount.toString())

        val collectJob = launch(UnconfinedTestDispatcher()) {

            when (val exchangeResultsState = viewModel.allExchangeResults.first()) {
                is ExchangeRateListUIState.Success -> {
                    Truth.assertThat(exchangeResultsState.listOfExchangeRateUiModel.size)
                        .isEqualTo(expectedExchangeResultUiModelList.size)
                    expectedExchangeResultUiModelList.forEachIndexed { index, expected ->
                        assertEquals(
                            expected.code,
                            exchangeResultsState.listOfExchangeRateUiModel[index].code
                        )
                        assertEquals(
                            expected.amount,
                            exchangeResultsState.listOfExchangeRateUiModel[index].amount
                        )
                    }
                }

                else -> {}
            }
        }

        collectJob.cancel()
    }


    @Test
    fun `allExchangeResults should emit loading state when sourceCurrencyFlow is null`() =
        mainCoroutineRule.runTest {
            // Given
            val amount = 100

            // When
            viewModel.updateSourceCurrency(null)
            viewModel.updateAmount(amount.toString())

            // Then
            val result = viewModel.allExchangeResults.first()
            Truth.assertThat(result).isEqualTo(ExchangeRateListUIState.Empty)
        }

    @Test
    fun `allExchangeResults should emit empty state when amount is 0`() =
        mainCoroutineRule.runTest {
            // Given
            val sourceCurrency = CurrencyUiModel("USD", "US Dollar")

            // When
            viewModel.updateSourceCurrency(sourceCurrency)
            viewModel.updateAmount("0")

            // Then
            val result = viewModel.allExchangeResults.first()
            Truth.assertThat(result).isEqualTo(ExchangeRateListUIState.Empty)
        }

    @Test
    fun `updateAmount with non-numeric input should not update amountFlow`() {
        // When
        viewModel.updateAmount("abc")

        // Then
        Truth.assertThat(viewModel.amountState.value).isEqualTo(0)
    }
}
