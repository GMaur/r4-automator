package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import org.springframework.context.annotation.Bean

class TwoFactorAuthenticationProviderConfiguration {

    @Bean
    fun twoFactorAuthenticationProvider(): TwoFactorAuthenticationProvider {
        return ConsoleTwoFactorAuthenticationProvider.aNew()
    }

}