package com.currencyconverter.core.data.date

import com.currencyconverter.core.testing.utils.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataUtilsTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun testIsRequiredToUpdateLocalData_whenLastUpdatedTimeIsNull() = mainCoroutineRule.runTest {
        val timeHelperMock = mockk<TimeHelper>()
        every { timeHelperMock.currentTimeMillis } returns 1662130918540

        val result = isRequiredToUpdateLocalData(timeHelperMock, null)
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun testIsRequiredToUpdateLocalData_whenLastUpdatedTimeIsOlderThanCacheLimit() =
        mainCoroutineRule.runTest {
            val currentTimeMillis = 1662130918540
            val lastUpdatedTime = currentTimeMillis - CACHE_LIMIT_DURATION - 1000

            val timeHelperMock = mockk<TimeHelper>()
            every { timeHelperMock.currentTimeMillis } returns currentTimeMillis

            val result = isRequiredToUpdateLocalData(timeHelperMock, lastUpdatedTime)
            Truth.assertThat(result).isTrue()
        }

    @Test
    fun testIsRequiredToUpdateLocalData_whenLastUpdatedTimeIsWithinCacheLimit() =
        mainCoroutineRule.runTest {
            val currentTimeMillis = 1662130918540
            val lastUpdatedTime = currentTimeMillis - CACHE_LIMIT_DURATION + 1000

            val timeHelperMock = mockk<TimeHelper>()
            every { timeHelperMock.currentTimeMillis } returns currentTimeMillis

            val result = isRequiredToUpdateLocalData(timeHelperMock, lastUpdatedTime)
            Truth.assertThat(result).isFalse()
        }

    @After
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }
}
