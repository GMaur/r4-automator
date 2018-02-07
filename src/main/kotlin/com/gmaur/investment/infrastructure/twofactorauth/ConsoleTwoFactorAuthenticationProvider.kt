package com.gmaur.investment.infrastructure.twofactorauth

import java.io.BufferedReader
import java.io.PrintStream

class ConsoleTwoFactorAuthenticationProvider(private val input: BufferedReader, private val out: PrintStream) : TwoFactorAuthenticationProvider {
    override fun request(): String {
        out.print("Input your 2FA code:")
        return input.readLine()
    }
}