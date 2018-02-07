package com.gmaur.investment.infrastructure.twofactorauth

interface TwoFactorAuthenticationProvider {
    fun request(): String
}