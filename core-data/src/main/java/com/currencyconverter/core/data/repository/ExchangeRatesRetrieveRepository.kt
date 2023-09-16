package com.currencyconverter.core.data.repository

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.date.TimeHelper
import com.currencyconverter.core.data.date.isRequiredToUpdateLocalData
import com.currencyconverter.core.data.mapper.exchangerate.ExchangeRateMapperForEntityToDataModel
import com.currencyconverter.core.data.mapper.exchangerate.ExchangeRateMapperForResponseToEntity
import com.currencyconverter.core.data.model.ExchangeRateModel
import com.currencyconverter.core.database.datasource.ExchangeRateLocalDataSource
import com.currencyconverter.core.remote.datasource.ExchangeRateRemoteDataSource
import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface ExchangeRatesRetrieveRepository {

    fun getExchangeRatesByBase(): Flow<ResponseDataStateHandler<List<ExchangeRateModel>>>
}

class ExchangeRatesRetrieveRepositoryImpl @Inject constructor(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val exchangeRateLocalDataSource: ExchangeRateLocalDataSource,
    private val currencyConverterAppDataStore: CurrencyConverterAppDataStore,
    private val exchangeRateMapperForResponseToEntity: ExchangeRateMapperForResponseToEntity,
    private val exchangeRateMapperForEntityToDataModel: ExchangeRateMapperForEntityToDataModel,
    private val timeHelper: TimeHelper
) : ExchangeRatesRetrieveRepository {

    override fun getExchangeRatesByBase(): Flow<ResponseDataStateHandler<List<ExchangeRateModel>>> =
        callDataFlow {

            val isRequiredToUpdateData = isRequiredToUpdateLocalData(
                timeHelper,
                currencyConverterAppDataStore.getLastUpdateTimeForExchangeRateList()
            )

            if (isRequiredToUpdateData) {
                val exchangeRateCurrencyResponse =
                    exchangeRateRemoteDataSource.getLatestExchangeRate()
                exchangeRateLocalDataSource.saveExchangeRates(
                    exchangeRateMapperForResponseToEntity(
                        exchangeRateCurrencyResponse
                    )
                )
                currencyConverterAppDataStore.setLastUpdateTimeForExchangeRateList(timeHelper.currentTimeMillis)
                currencyConverterAppDataStore.setLatestBase(exchangeRateCurrencyResponse.base)
            }

            val listOfAllExchangeRates =
                exchangeRateMapperForEntityToDataModel(
                    exchangeRateLocalDataSource.getAllExchangeRatesByBase(
                        currencyConverterAppDataStore.latestBase.first()
                    )
                )

            if (listOfAllExchangeRates.isNotEmpty()) {
                emit(ResponseDataStateHandler.Success(listOfAllExchangeRates))
            } else {
                emit(ResponseDataStateHandler.Empty)
            }
        }
}
