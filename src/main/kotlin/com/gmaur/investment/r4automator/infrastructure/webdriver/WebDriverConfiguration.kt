package com.gmaur.investment.r4automator.infrastructure.webdriver

import com.gmaur.investment.r4automator.infrastructure.configuration.PropertiesPropertyLoaderFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationProperties
@PropertySource("classpath:/application.properties", factory = PropertiesPropertyLoaderFactory::class)
class WebDriverConfiguration {
    var pathDriver: String = ""
    var nameDriver: String = ""
    var exeDriver: String = ""
    var url: String = ""

    @Bean
    fun driverBean(): WebDriver {
        System.setProperty(nameDriver, pathDriver + exeDriver)
        var driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().maximize()
        driver.manage().window().fullscreen()
        return driver

    }
}