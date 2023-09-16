package com.currencyconverter.core.remote.customexception

import java.io.IOException

/**
 * Data class representing the error response body received from the API.
 * @param error Whether the response represents an error.
 * @param status The HTTP status code of the error.
 * @param message A brief message describing the error.
 * @param description A detailed description of the error.
 */
data class ApiErrorBody(
    val error: Boolean, val status: Int, val message: String?, val description: String?
)


/**
 * Custom IOException class representing an API error.
 * @param message A message describing the API error.
 */
class APIErrorException(message: String) : IOException(message)

/**
 * Custom IOException class representing an API error for "No Data Found" responses.
 * @param errorResponse The error response message from the API.
 */
class NoDataFoundException(errorResponse: String) : IOException(errorResponse)

/**
 * Custom IOException class representing an API error for "Internal Server Error" responses.
 * @param errorResponse The error response message from the API.
 */
class InternalServerException(errorResponse: String) : IOException(errorResponse)

/**
 * Custom IOException class representing an API error for "Unauthorized" responses.
 * @param errorResponse The error response message from the API.
 */
class UnAuthorizationException(errorResponse: String) : IOException(errorResponse)

/**
 * Custom IOException class representing an API error for "Unsupported Request" responses.
 * @param errorResponse The error response message from the API.
 */
class UnSupportedRequestException(errorResponse: String) : IOException(errorResponse)