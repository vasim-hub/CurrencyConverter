package com.currencyconverter.core.data.date

const val CACHE_LIMIT_DURATION = 30 * 60 * 1000

fun isRequiredToUpdateLocalData(timeHelper: TimeHelper, lastUpdatedTime: Long?): Boolean {
    return lastUpdatedTime?.let { lastUpdate ->
        timeHelper.currentTimeMillis - lastUpdate >= CACHE_LIMIT_DURATION
    } ?: true
}
