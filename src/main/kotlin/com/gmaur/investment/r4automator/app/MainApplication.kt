package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProviderConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(TwoFactorAuthenticationProviderConfiguration::class)
@ComponentScan(basePackages = arrayOf("com.gmaur.investment.r4automator"))
class MainApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainApplication::class.java, *args)
}