package com.currencyconverter.core.data.repository

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.data.date.TimeHelper
import com.currencyconverter.core.data.date.isRequiredToUpdateLocalData
import com.currencyconverter.core.data.mapper.currencylist.CurrencyMapperForEntityToDataModel
import com.currencyconverter.core.data.mapper.currencylist.CurrencyMapperForResponseToEntity
import com.currencyconverter.core.data.model.CurrencyModel
import com.currencyconverter.core.database.datasource.CurrencyLocalDataSource
import com.currencyconverter.core.remote.datasource.CurrencyRemoteDataSource
import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrencyRepository {
    fun getCurrencyList(): Flow<ResponseDataStateHandler<List<CurrencyModel>>>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyLocalDataSource: CurrencyLocalDataSource,
    private val currencyConverterAppDataStore: CurrencyConverterAppDataStore,
    private val currencyMapperForResponseToEntity: CurrencyMapperForResponseToEntity,
    private val currencyMapperForEntityToDataModel: CurrencyMapperForEntityToDataModel,
    private val timeHelper: TimeHelper
) : CurrencyRepository {

    override fun getCurrencyList(): Flow<ResponseDataStateHandler<List<CurrencyModel>>> =
        callDataFlow {

            val isRequiredToUpdateData = isRequiredToUpdateLocalData(
                timeHelper,
                currencyConverterAppDataStore.getLastUpdateTimeForCurrencyList()
            )

            if (isRequiredToUpdateData) {
                val currencyListFromRemote = currencyRemoteDataSource.getCurrencies()
                currencyLocalDataSource.saveCurrencies(
                    currencyMapperForResponseToEntity(
                        currencyListFromRemote
                    )
                )
                currencyConverterAppDataStore.setLastUpdateTimeForCurrencyList(timeHelper.currentTimeMillis)
            }

            val listOfAllCurrencies = currencyMapperForEntityToDataModel(
                currencyLocalDataSource.getAllCurrencies()
            )

            if (listOfAllCurrencies.isNotEmpty()) {
                emit(ResponseDataStateHandler.Success(listOfAllCurrencies))
            } else {
                emit(ResponseDataStateHandler.Empty)
            }
        }
}
