package com.currencyconverter.core.data.date

import javax.inject.Inject

interface TimeHelper {

    var currentTimeMillis: Long
}

class TimeHelperImpl @Inject constructor() : TimeHelper {

    override var currentTimeMillis: Long = System.currentTimeMillis()
}
