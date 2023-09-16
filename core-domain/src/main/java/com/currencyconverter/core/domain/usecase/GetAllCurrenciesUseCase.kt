package com.currencyconverter.core.domain.usecase

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.data.repository.CurrencyRepository
import com.currencyconverter.core.domain.di.IoDispatcher
import com.currencyconverter.core.domain.mapper.responsemapper.CurrencyMapperForDataToDomain
import com.currencyconverter.core.domain.model.CurrencyUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    currencyMapperForDataToDomain: CurrencyMapperForDataToDomain,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : BaseDomainUseCase<Unit?, List<CurrencyModel>, List<CurrencyUiModel>>(
    coroutineDispatcher,
    currencyMapperForDataToDomain
) {
    override fun execute(params: Unit?): Flow<ResponseDataStateHandler<List<CurrencyModel>>> {
        return currencyRepository.getCurrencyList()
    }
}
