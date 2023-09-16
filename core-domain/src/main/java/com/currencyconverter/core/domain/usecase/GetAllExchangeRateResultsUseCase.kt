package com.currencyconverter.core.domain.usecase

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.data.repository.ExchangeRatesRetrieveRepository
import com.currencyconverter.core.domain.di.IoDispatcher
import com.currencyconverter.core.domain.mapper.responsemapper.ExchangeRateMapperForDataToDomain
import com.currencyconverter.core.domain.model.ExchangeResultUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExchangeRateResultsUseCase @Inject constructor(
    private val exchangeRatesRetrieveRepository: ExchangeRatesRetrieveRepository,
    private val exchangeRateMapperForDataToDomain: ExchangeRateMapperForDataToDomain,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : BaseDomainUseCase<Pair<String?, Double>, List<ExchangeRateModel>, List<ExchangeResultUiModel>>(
    coroutineDispatcher, exchangeRateMapperForDataToDomain
) {
    public override fun execute(params: Pair<String?, Double>): Flow<ResponseDataStateHandler<List<ExchangeRateModel>>> {
        exchangeRateMapperForDataToDomain.source = params.first
        exchangeRateMapperForDataToDomain.amount = params.second
        return exchangeRatesRetrieveRepository.getExchangeRatesByBase()
    }
}
