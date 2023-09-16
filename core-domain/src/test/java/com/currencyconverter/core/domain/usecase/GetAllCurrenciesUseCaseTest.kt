package com.currencyconverter.core.domain.usecase

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.data.repository.CurrencyRepository
import com.currencyconverter.core.domain.DomainStateHandler
import com.currencyconverter.core.domain.mapper.responsemapper.CurrencyMapperForDataToDomain
import com.currencyconverter.core.domain.model.CurrencyUiModel
import com.currencyconverter.core.shared.ErrorStatusEnum
import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllCurrenciesUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `test execute success`() = mainCoroutineRule.runTest {
        // Given
        val currencyRepository = mockk<CurrencyRepository>()
        val currencyMapper = CurrencyMapperForDataToDomain()

        val useCase =
            GetAllCurrenciesUseCase(currencyRepository, currencyMapper, Dispatchers.Default)

        val currencyModelList = listOf(
            CurrencyModel("AED", "United Arab Emirates Dirham"),
            CurrencyModel("AFN", "Afghan Afghani"),
            CurrencyModel("ALL", "Albanian Lek")
        )

        val responseDataStateHandler = ResponseDataStateHandler.Success(currencyModelList)

        // Mock the repository response using coEvery from MockK
        coEvery { currencyRepository.getCurrencyList() } returns flowOf(responseDataStateHandler)

        // When
        val result: DomainStateHandler<List<CurrencyUiModel>> = useCase(Unit).toList().first()

        // Then
        val expectedCurrencyUiModelList = listOf(
            CurrencyUiModel("AED", "AED - United Arab Emirates Dirham"),
            CurrencyUiModel("AFN", "AFN - Afghan Afghani"),
            CurrencyUiModel("ALL", "ALL - Albanian Lek")
        )

        assertThat(result).isEqualTo(DomainStateHandler.Success(expectedCurrencyUiModelList))
    }

    @Test
    fun `test execute loading`() = mainCoroutineRule.runTest {
        // Given
        val currencyRepository = mockk<CurrencyRepository>()
        val currencyMapper = CurrencyMapperForDataToDomain()

        val useCase =
            GetAllCurrenciesUseCase(currencyRepository, currencyMapper, Dispatchers.Unconfined)

        val responseDataStateHandler = ResponseDataStateHandler.Loading

        runBlocking {
            // Mock the repository response using coEvery from MockK
            coEvery { currencyRepository.getCurrencyList() } returns flowOf(responseDataStateHandler)

            // When
            val result: DomainStateHandler<List<CurrencyUiModel>> = useCase(Unit).toList().first()

            // Then
            assertThat(result).isEqualTo(DomainStateHandler.Loading)
        }
    }

    @Test
    fun `test execute empty`() = mainCoroutineRule.runTest {
        // Given
        val currencyRepository = mockk<CurrencyRepository>()
        val currencyMapper = CurrencyMapperForDataToDomain()

        val useCase =
            GetAllCurrenciesUseCase(currencyRepository, currencyMapper, Dispatchers.Unconfined)

        val responseDataStateHandler = ResponseDataStateHandler.Empty

        // Mock the repository response using coEvery from MockK
        coEvery { currencyRepository.getCurrencyList() } returns flowOf(responseDataStateHandler)

        // When
        val result: DomainStateHandler<List<CurrencyUiModel>> = useCase(Unit).toList().first()

        // Then
        assertThat(result).isEqualTo(DomainStateHandler.Empty)
    }

    @Test
    fun `test execute error`() = mainCoroutineRule.runTest {
        // Given
        val currencyRepository = mockk<CurrencyRepository>()
        val currencyMapper = CurrencyMapperForDataToDomain()

        val useCase =
            GetAllCurrenciesUseCase(currencyRepository, currencyMapper, Dispatchers.Unconfined)

        val errorMessage = "An error occurred."
        val responseDataStateHandler =
            ResponseDataStateHandler.Error(errorMessage, ErrorStatusEnum.API_ERROR)

        // Mock the repository response using coEvery from MockK
        coEvery { currencyRepository.getCurrencyList() } returns flowOf(responseDataStateHandler)

        // When
        val result: DomainStateHandler<List<CurrencyUiModel>> = useCase(Unit).toList().first()

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
