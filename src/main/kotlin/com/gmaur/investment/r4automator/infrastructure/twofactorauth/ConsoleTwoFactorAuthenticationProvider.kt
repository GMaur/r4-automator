package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream

class ConsoleTwoFactorAuthenticationProvider(private val input: BufferedReader, private val out: PrintStream) : TwoFactorAuthenticationProvider {
    override fun request(): String {
        out.print("Input your 2FA code:")
        return input.readLine()
    }

    companion object {
        fun aNew(): ConsoleTwoFactorAuthenticationProvider {
            return ConsoleTwoFactorAuthenticationProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)
        }
    }
}