package com.gmaur.investment.r4automator.infrastructure.login

import com.gmaur.investment.r4automator.infrastructure.configuration.PropertiesPropertyLoaderFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties
@PropertySource("classpath:/private.properties", factory = PropertiesPropertyLoaderFactory::class)
class LoginConfiguration {
    var username: String = ""
    var password: String = ""
    var nif: String = ""
    var url: String = ""
}