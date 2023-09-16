package com.currencyconverter.core.domain

import com.currencyconverter.core.shared.ErrorStatusEnum

sealed class DomainStateHandler<out T> {
    data class Success<out T>(val data: T) : DomainStateHandler<T>()
    object Loading : DomainStateHandler<Nothing>()
    object Empty : DomainStateHandler<Nothing>()
    data class Error(
        val message: String?, val errorStatusEnum: ErrorStatusEnum = ErrorStatusEnum.API_ERROR
    ) : DomainStateHandler<Nothing>()
}
