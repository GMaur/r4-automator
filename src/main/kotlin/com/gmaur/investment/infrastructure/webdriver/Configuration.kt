package com.gmaur.investment.infrastructure.webdriver

import com.gmaur.investment.infrastructure.configuration.PropertiesPropertyLoaderFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties
@PropertySource("classpath:/application.properties", factory = PropertiesPropertyLoaderFactory::class)
class Configuration {
    var pathDriver: String = ""
    var nameDriver: String = ""
    var exeDriver: String = ""
    var url: String = ""
}