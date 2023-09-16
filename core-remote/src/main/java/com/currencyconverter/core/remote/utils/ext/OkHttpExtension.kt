package com.currencyconverter.core.remote.utils.ext

import com.currencyconverter.core.remote.BuildConfig
import com.currencyconverter.core.remote.customexception.APIErrorException
import com.currencyconverter.core.remote.customexception.ApiErrorBody
import com.currencyconverter.core.remote.customexception.InternalServerException
import com.currencyconverter.core.remote.customexception.NoDataFoundException
import com.currencyconverter.core.remote.customexception.UnAuthorizationException
import com.currencyconverter.core.remote.customexception.UnSupportedRequestException
import com.currencyconverter.core.remote.utils.RequireHeaders
import com.squareup.moshi.JsonAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Invocation


/**
 * Extension function to check if a specific annotation is present on the Retrofit Request.
 * @param annotationClass The class of the annotation to check.
 * @return true if the annotation is present on the Request, false otherwise.
 */
fun <T : Annotation> Request.annotationFor(annotationClass: Class<T>) =
    tag(Invocation::class.java)?.method()?.isAnnotationPresent(annotationClass)

/**
 * Extension function to check if the Request requires additional headers by looking for the
 * 'RequireHeaders' annotation on the method.
 * @return true if the Request requires additional headers, false otherwise.
 */
fun Request.isRequiredHeaders(): Boolean = annotationFor(RequireHeaders::class.java) == true


/**
 * Extension function to set the OpenExchange API key as an Authorization header in the Request.Builder.
 * @param tokenPrefix The prefix to be used for the Authorization header.
 */
fun Request.Builder.setOpenExchangeApiKey(tokenPrefix: String) =
    addHeader("Authorization", "$tokenPrefix ${BuildConfig.OPEN_EXCHANGE_API_KEY}")

/**
 * Extension function to add an interceptor to the OkHttpClient for handling headers.
 * The interceptor checks if the Request requires additional headers and adds them accordingly.
 * @param setOpenExchangeApiKeyHeader Lambda function to customize the Request.Builder for adding the API key header.
 */
fun OkHttpClient.Builder.setHeaders(
    setOpenExchangeApiKeyHeader: Request.Builder.() -> Request.Builder
) =
    addInterceptor { chain ->
        val request = if (chain.request().isRequiredHeaders()) {
            chain.request().newBuilder().setOpenExchangeApiKeyHeader().build()
        } else {
            chain.request()
        }
        chain.proceed(request)
    }

/**
 * Extension function to add an interceptor to the OkHttpClient for handling API error responses.
 * The interceptor handles various HTTP error codes and throws appropriate exceptions for error handling.
 * @param jsonAdapter The JsonAdapter used for parsing the error response body.
 */
fun OkHttpClient.Builder.apiHandleErrors(jsonAdapter: JsonAdapter<ApiErrorBody>) =
    addInterceptor { chain ->
        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            response
        } else {
            val code = response.code
            val responseBody = response.body

            if (responseBody != null) {

                val responseBodyString = responseBody.string()
                val apiErrorBody: ApiErrorBody? = jsonAdapter.fromJson(responseBodyString)
                val errorDescriptionMessage = apiErrorBody?.description ?: ""

                when (code) {
                    400 -> {
                        throw UnSupportedRequestException(errorDescriptionMessage)
                    }

                    404 -> {
                        throw NoDataFoundException(errorDescriptionMessage)
                    }

                    401 -> {
                        throw UnAuthorizationException(errorDescriptionMessage)
                    }

                    403, 429 -> {
                        throw APIErrorException(errorDescriptionMessage)
                    }

                    else -> {
                        throw InternalServerException(errorDescriptionMessage)
                    }
                }
            } else {
                /** Required to define appropriate code and message which return by api*/
                throw APIErrorException("Unknown Error")
            }
        }
    }


/**
 * Extension function to add a logging interceptor to the OkHttpClient.
 * The logging level is determined by whether the app is in DEBUG mode or not.
 * If in DEBUG mode, full request and response details are logged (Level.BODY),
 * otherwise, no logging is performed (Level.NONE).
 */
fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    } else {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }
    addInterceptor(loggingInterceptor)
    return this
}
