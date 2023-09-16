package com.currencyconverter.core.domain.usecase

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.domain.DomainStateHandler
import com.currencyconverter.core.domain.mapper.MapperDataToDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class BaseDomainUseCase<Parameters, DataResponseState, DomainResponseState>(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mapper: MapperDataToDomain<DataResponseState, DomainResponseState>
) {
    operator fun invoke(params: Parameters): Flow<DomainStateHandler<DomainResponseState>> =
        execute(params).catch { e -> DomainStateHandler.Error(e.message) }
            .map { dataResponseHandler ->
                when (dataResponseHandler) {
                    is ResponseDataStateHandler.Success -> DomainStateHandler.Success(
                        mapper(dataResponseHandler.data)
                    )

                    is ResponseDataStateHandler.Error -> DomainStateHandler.Error(
                        dataResponseHandler.message, dataResponseHandler.errorStatusEnum
                    )

                    ResponseDataStateHandler.Loading -> DomainStateHandler.Loading
                    else -> DomainStateHandler.Empty
                }
            }.flowOn(coroutineDispatcher)

    protected abstract fun execute(params: Parameters): Flow<ResponseDataStateHandler<DataResponseState>>
}