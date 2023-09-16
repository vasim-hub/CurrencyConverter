package com.currencyconverter.core.data

import com.currencyconverter.core.shared.ErrorStatusEnum

sealed class ResponseDataStateHandler<out T> {
    data class Success<out T>(val data: T) : ResponseDataStateHandler<T>()
    object Loading : ResponseDataStateHandler<Nothing>()
    object Empty : ResponseDataStateHandler<Nothing>()
    data class Error(val message: String?, val errorStatusEnum: ErrorStatusEnum) :
        ResponseDataStateHandler<Nothing>()
}
