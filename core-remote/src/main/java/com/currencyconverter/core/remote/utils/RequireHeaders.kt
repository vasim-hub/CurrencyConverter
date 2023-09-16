package com.currencyconverter.core.remote.utils

/**
 * Annotation used to mark specific Retrofit API endpoints that require additional headers in the requests.
 *
 * When this annotation is applied to a function in the Retrofit service interface, it indicates that
 * the corresponding API endpoint requires custom headers to be added to the request, such as an
 * "Authorization" header for authentication purposes.
 *
 * Usage example:
 *
 * // Your Retrofit service interface
 * interface ApiService {
 *
 *     @RequireHeaders
 *     @GET("endpoint/with/headers")
 *     suspend fun apiCallWithHeaders(): YourResponseModel
 *
 *     @GET("endpoint/without/headers")
 *     suspend fun apiCallWithoutHeaders(): YourResponseModel
 * }
 *
 * In this example, 'apiCallWithHeaders()' is marked with the 'RequireHeaders' annotation,
 * indicating that headers should be added for this specific endpoint, while 'apiCallWithoutHeaders()'
 * does not require any additional headers.
 *
 * By using this annotation, you can dynamically handle the addition of headers for specific API
 * endpoints in an OkHttp interceptor, allowing you to customize the behavior of your network calls.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RequireHeaders