package com.gmaur.investment.r4automator.infrastructure.twofactorauth

interface TwoFactorAuthenticationProvider {
    fun request(): String
}