package com.currencyconverter.core.shared

/**
 * This Enum is used for handling different HTTP status,
 * In this it is possible to add Custom Error status which is based
 * on API Response.
 **/
enum class ErrorStatusEnum {

    /** Bad Request HTTP Status Code */
    BAD_REQUEST,

    /** UnAuthorization HTTP Status Code */
    UN_AUTHORIZATION,

    /** UNSUPPORTED Status Code */
    UNSUPPORTED,

    /** No data found Status Code */
    NO_DATA_FOUND,

    /** Internal server HTTP Status Code */
    INTERNAL_SERVER_ERROR,

    /** No Internet handle Status Code*/
    NO_INTERNET,

    /** Failed to insert an item in local room table*/
    FAILED_TO_INSERT_ITEM_IN_LOCAL,

    /** Failed to delete an item in local room table*/
    FAILED_TO_DELETE_ITEM_IN_LOCAL,

    /** Item not exist local room table*/
    ITEM_NOT_EXIST_IN_LOCAL_DB,

    /** No data in local db*/
    NO_DATA_IN_LOCAL_DB,

    /** Item already exist local room table*/
    ITEM_ALREADY_EXIST_IN_LOCAL_DB,

    /** API error HTTP Status Code */
    API_ERROR
}