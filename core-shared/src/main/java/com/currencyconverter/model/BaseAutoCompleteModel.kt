package com.currencyconverter.model

interface BaseAutoCompleteModel {
    fun filter(query: String): Boolean
}
