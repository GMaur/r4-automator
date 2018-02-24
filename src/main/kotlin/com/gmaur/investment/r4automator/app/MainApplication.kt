package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProviderConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import kotlin.system.exitProcess

@Configuration
@EnableAutoConfiguration
@Import(TwoFactorAuthenticationProviderConfiguration::class)
@ComponentScan(basePackages = arrayOf("com.gmaur.investment.r4automator"))
class MainApplication

fun main(args: Array<String>) {
    if (!inDebugMode()) {
        println("This application is in alpha mode - please execute it in debug mode only")
        exitProcess(1)
    }
    SpringApplication.run(MainApplication::class.java, *args)
}

fun inDebugMode(): Boolean {
    return java.lang.management.ManagementFactory.getRuntimeMXBean().inputArguments.toString().contains("-agentlib:jdwp")
}
