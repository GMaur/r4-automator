package com.gmaur.investment.infrastructure.funds

import com.gmaur.investment.infrastructure.configuration.PropertiesPropertyLoaderFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties
@PropertySource("classpath:/funds.properties", factory = PropertiesPropertyLoaderFactory::class)
class FundsConfiguration {
    var url: String = ""
}