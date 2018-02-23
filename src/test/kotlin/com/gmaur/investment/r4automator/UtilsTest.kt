package com.gmaur.investment.r4automator

import org.mockito.Mockito

object UtilsTest {
    fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}