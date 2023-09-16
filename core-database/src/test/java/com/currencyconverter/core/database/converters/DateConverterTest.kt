package com.currencyconverter.core.database.converters

import com.google.common.truth.Truth
import org.junit.Test
import java.util.Calendar

class DateConverterTest {
    private val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1998)
        set(Calendar.MONTH, Calendar.SEPTEMBER)
        set(Calendar.DAY_OF_MONTH, 4)
    }

    @Test
    fun `Convert from date to timestamp`() {
        Truth.assertThat(DateConverter.fromDate(cal.time)).isEqualTo(cal.timeInMillis)
    }

    @Test
    fun `Convert from timestamp to date`() {
        Truth.assertThat(DateConverter.toDate(cal.timeInMillis)).isEqualTo(cal.time)
    }
}