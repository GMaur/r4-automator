package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmaur.investment.r4automator.infrastructure.JSONMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TwoFactorAuthenticationProviderConfiguration {

    @Bean
    fun twoFactorAuthenticationProvider(): TwoFactorAuthenticationProvider {
        return ConsoleTwoFactorAuthenticationProvider.aNew()
    }

    @Bean
    fun jsonMapper(): ObjectMapper {
        return JSONMapper.aNew()
    }

}