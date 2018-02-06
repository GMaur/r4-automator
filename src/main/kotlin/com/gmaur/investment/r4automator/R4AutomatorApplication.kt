package com.gmaur.investment.r4automator

import com.gmaur.investment.infrastructure.LoginPage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.env.PropertiesPropertySourceLoader
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.support.DefaultPropertySourceFactory
import org.springframework.core.io.support.EncodedResource
import org.springframework.stereotype.Component
import java.net.URI
import java.util.concurrent.TimeUnit


// Source: https://stackoverflow.com/questions/45880494/configurationproperties-loading-list-from-yml#45882088

@Component
@ConfigurationProperties
@PropertySource("classpath:/application.properties", factory = PropertiesPropertyLoaderFactory::class)
class Configuration {
    var pathDriver: String = ""
    var nameDriver: String = ""
    var exeDriver: String = ""
    var url: String = ""
}

@Component
@ConfigurationProperties
@PropertySource("classpath:/private.properties", factory = PropertiesPropertyLoaderFactory::class)
class LoginConfiguration {
    var username: String = ""
    var password: String = ""
    var nif: String = ""
}

open class PropertiesPropertyLoaderFactory : DefaultPropertySourceFactory() {
    override fun createPropertySource(name: String?, resource: EncodedResource): org.springframework.core.env.PropertySource<*> {
        return PropertiesPropertySourceLoader().load(resource.resource.filename, resource.resource, null)
    }
}

@SpringBootApplication()
@EnableAutoConfiguration
class R4AutomatorApplication {
    private val config: Configuration

    lateinit var driver: WebDriver

    constructor(config: Configuration, loginConfiguration: LoginConfiguration) {
        this.config = config
        setup()

        doWork(loginConfiguration)

        closeDriver()
    }

    private fun doWork(loginConfiguration: LoginConfiguration) {
        LoginPage(driver).login(loginConfiguration)
    }


    final fun setup() {
        System.setProperty(this.config.nameDriver, this.config.pathDriver + this.config.exeDriver)
        driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().maximize()
        driver.manage().window().fullscreen()
        driver.get(URI(this.config.url).toString())
    }

    final fun closeDriver() {
        this.driver.close()
    }

}

fun main(args: Array<String>) {
    runApplication<R4AutomatorApplication>(*args)
}
