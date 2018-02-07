package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.BufferedReader

class ConsoleTwoFactorAuthenticationProviderTest {
    @Test
    fun read_from_the_console() {
        val bufferedReader = org.mockito.Mockito.mock(BufferedReader::class.java)
        `when`(bufferedReader.readLine()).thenReturn("123456")
        val sut = ConsoleTwoFactorAuthenticationProvider(bufferedReader, System.out)

        val code = sut.request()

        assertThat(code, `is`("123456"))
    }
}