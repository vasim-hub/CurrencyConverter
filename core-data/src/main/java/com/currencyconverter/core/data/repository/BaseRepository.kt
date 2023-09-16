package com.currencyconverter.core.data.repository

import com.currencyconverter.core.data.ResponseDataStateHandler
import com.currencyconverter.core.remote.customexception.InternalServerException
import com.currencyconverter.core.remote.customexception.NoDataFoundException
import com.currencyconverter.core.remote.customexception.UnAuthorizationException
import com.currencyconverter.core.remote.customexception.UnSupportedRequestException
import com.currencyconverter.core.shared.ErrorStatusEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Base repository class that provides a generic function to call data flow and handle exceptions.
 * @param T The type of data being fetched from the data flow.
 * @param block The suspend function that collects data into the flow.
 * @return A flow of ResponseDataStateHandler representing the state of the data flow.
 */
fun <T> callDataFlow(block: suspend FlowCollector<ResponseDataStateHandler<T>>.() -> Unit): Flow<ResponseDataStateHandler<T>> =
    flow {
        block()
    }.catch { e ->
        emit(handleAPIException(e))
    }

/**
 * Function to handle API-related exceptions and convert them into ResponseDataStateHandler.Error.
 * @param error The throwable representing the API-related exception.
 * @return ResponseDataStateHandler.Error with the appropriate ErrorStatusEnum based on the exception type.
 */
fun handleAPIException(error: Throwable): ResponseDataStateHandler.Error {
    return when (error) {
        is SocketTimeoutException, is UnknownHostException -> ResponseDataStateHandler.Error(
            error.message,
            ErrorStatusEnum.NO_INTERNET
        )

        is UnSupportedRequestException -> ResponseDataStateHandler.Error(
            error.message,
            ErrorStatusEnum.UNSUPPORTED
        )

        is NoDataFoundException -> ResponseDataStateHandler.Error(
            error.message,
            ErrorStatusEnum.NO_DATA_FOUND
        )

        is UnAuthorizationException -> ResponseDataStateHandler.Error(
            error.message,
            ErrorStatusEnum.UN_AUTHORIZATION
        )

        is InternalServerException -> ResponseDataStateHandler.Error(
            error.message,
            ErrorStatusEnum.INTERNAL_SERVER_ERROR
        )

        else -> ResponseDataStateHandler.Error(error.message, ErrorStatusEnum.API_ERROR)
    }
}
