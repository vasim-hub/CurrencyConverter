package com.currencyconverter.core.domain.usecase

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.data.repository.ExchangeRatesRetrieveRepository
import com.currencyconverter.core.domain.DomainStateHandler
import com.currencyconverter.core.domain.mapper.responsemapper.ExchangeRateMapperForDataToDomain
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import com.currencyconverter.core.shared.ErrorStatusEnum
import com.currencyconverter.core.shared.numberFormat
import com.currencyconverter.core.testing.fakedata.baseCurrency
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllExchangeRateResultsUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `test execute success with valid source and amount`() = mainCoroutineRule.runTest {
        // Given
        val exchangeRatesRetrieveRepository = mockk<ExchangeRatesRetrieveRepository>()
        val exchangeRateMapper = ExchangeRateMapperForDataToDomain()

        val useCase = GetAllExchangeRateResultsUseCase(
            exchangeRatesRetrieveRepository, exchangeRateMapper, Dispatchers.Unconfined
        )

        val sourceCurrency = "USD"
        val amount = 100.0
        val exchangeRateModelList = listOf(
            ExchangeRateModel("USD", baseCurrency, 1.0),
            ExchangeRateModel("EUR", baseCurrency, 0.85),
            ExchangeRateModel("GBP", baseCurrency, 0.75)
        )

        val responseDataStateHandler = ResponseDataStateHandler.Success(exchangeRateModelList)


        // Mock the ExchangeRateMapperForDataToDomain properties
        exchangeRateMapper.source = sourceCurrency
        exchangeRateMapper.amount = amount

        // Mock the repository response using coEvery from MockK
        coEvery { exchangeRatesRetrieveRepository.getExchangeRatesByBase() } returns flowOf(
            responseDataStateHandler
        )

        // When
        val result: DomainStateHandler<List<ExchangeResultUiModel>> =
            useCase(Pair(sourceCurrency, amount)).toList().first()

        // Then
        val expectedExchangeResultUiModelList = listOf(
            ExchangeResultUiModel("USD 🇺🇸", numberFormat.format(100.0)),
            ExchangeResultUiModel("EUR 🇪🇺", numberFormat.format(85.0)),
            ExchangeResultUiModel("GBP 🇬🇧", numberFormat.format(75.0))
        )
        assertThat(result).isEqualTo(DomainStateHandler.Success(expectedExchangeResultUiModelList))

    }

    @Test
    fun `test execute success with empty source and amount`() = mainCoroutineRule.runTest {
        // Given
        val exchangeRatesRetrieveRepository = mockk<ExchangeRatesRetrieveRepository>()
        val exchangeRateMapper = ExchangeRateMapperForDataToDomain()

        val useCase = GetAllExchangeRateResultsUseCase(
            exchangeRatesRetrieveRepository, exchangeRateMapper, Dispatchers.Unconfined
        )

        val exchangeRateModelList = listOf(
            ExchangeRateModel("USD", baseCurrency, 1.0),
            ExchangeRateModel("EUR", baseCurrency, 0.85),
            ExchangeRateModel("GBP", baseCurrency, 0.75)
        )

        val responseDataStateHandler = ResponseDataStateHandler.Success(exchangeRateModelList)


        // Mock the ExchangeRateMapperForDataToDomain properties as empty
        exchangeRateMapper.source = null
        exchangeRateMapper.amount = 0.0

        // Mock the repository response using coEvery from MockK
        coEvery { exchangeRatesRetrieveRepository.getExchangeRatesByBase() } returns flowOf(
            responseDataStateHandler
        )

        // When
        val result: DomainStateHandler<List<ExchangeResultUiModel>> =
            useCase(Pair(null, 0.0)).toList().first()

        // Then
        assertThat(result).isEqualTo(DomainStateHandler.Success(emptyList<ExchangeResultUiModel>()))

    }


    @Test
    fun `test execute loading`() = mainCoroutineRule.runTest {
        // Given
        val exchangeRatesRetrieveRepository = mockk<ExchangeRatesRetrieveRepository>()
        val exchangeRateMapper = ExchangeRateMapperForDataToDomain()

        val useCase = GetAllExchangeRateResultsUseCase(
            exchangeRatesRetrieveRepository, exchangeRateMapper, Dispatchers.Unconfined
        )

        val responseDataStateHandler = ResponseDataStateHandler.Loading


        // Mock the ExchangeRateMapperForDataToDomain properties as empty
        exchangeRateMapper.source = null
        exchangeRateMapper.amount = 0.0

        // Mock the repository response using coEvery from MockK
        coEvery { exchangeRatesRetrieveRepository.getExchangeRatesByBase() } returns flowOf(
            responseDataStateHandler
        )

        // When
        val result: DomainStateHandler<List<ExchangeResultUiModel>> =
            useCase(Pair(null, 0.0)).toList().first()

        // Then
        assertThat(result).isEqualTo(DomainStateHandler.Loading)

    }

    @Test
    fun `test execute empty`() = mainCoroutineRule.runTest {
        // Given
        val exchangeRatesRetrieveRepository = mockk<ExchangeRatesRetrieveRepository>()
        val exchangeRateMapper = ExchangeRateMapperForDataToDomain()

        val useCase = GetAllExchangeRateResultsUseCase(
            exchangeRatesRetrieveRepository, exchangeRateMapper, Dispatchers.Unconfined
        )

        val responseDataStateHandler = ResponseDataStateHandler.Empty


        // Mock the ExchangeRateMapperForDataToDomain properties as empty
        exchangeRateMapper.source = null
        exchangeRateMapper.amount = 0.0

        // Mock the repository response using coEvery from MockK
        coEvery { exchangeRatesRetrieveRepository.getExchangeRatesByBase() } returns flowOf(
            responseDataStateHandler
        )

        // When
        val result: DomainStateHandler<List<ExchangeResultUiModel>> =
            useCase(Pair(null, 0.0)).toList().first()

        // Then
        assertThat(result).isEqualTo(DomainStateHandler.Empty)

    }

    @Test
    fun `test execute error`() = mainCoroutineRule.runTest {
        // Given
        val exchangeRatesRetrieveRepository = mockk<ExchangeRatesRetrieveRepository>()
        val exchangeRateMapper = ExchangeRateMapperForDataToDomain()

        val useCase = GetAllExchangeRateResultsUseCase(
            exchangeRatesRetrieveRepository, exchangeRateMapper, Dispatchers.Unconfined
        )

        val errorMessage = "An error occurred."
        val responseDataStateHandler =
            ResponseDataStateHandler.Error(errorMessage, ErrorStatusEnum.API_ERROR)


        // Mock the ExchangeRateMapperForDataToDomain properties as empty
        exchangeRateMapper.source = null
        exchangeRateMapper.amount = 0.0

        // Mock the repository response using coEvery from MockK
        coEvery { exchangeRatesRetrieveRepository.getExchangeRatesByBase() } returns flowOf(
            responseDataStateHandler
        )

        // When
        val result: DomainStateHandler<List<ExchangeResultUiModel>> =
            useCase(Pair(null, 0.0)).toList().first()

        // Then
        assertThat(result).isEqualTo(
            DomainStateHandler.Error(
                errorMessage,
                ErrorStatusEnum.API_ERROR
            )
        )

    }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}
