package com.gmaur.investment.r4automator.infrastructure.cash

import com.gmaur.investment.r4automator.infrastructure.configuration.PropertiesPropertyLoaderFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties
@PropertySource(value = ["classpath:application.properties"], factory = PropertiesPropertyLoaderFactory::class)
class CashConfiguration {
    var cashurl: String = ""
}