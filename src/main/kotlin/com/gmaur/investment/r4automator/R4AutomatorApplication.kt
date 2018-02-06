package com.gmaur.investment.r4automator

import com.gmaur.investment.infrastructure.login.LoginConfiguration
import com.gmaur.investment.infrastructure.login.LoginPage
import com.gmaur.investment.infrastructure.webdriver.Configuration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.net.URI
import java.util.concurrent.TimeUnit


// Source: https://stackoverflow.com/questions/45880494/configurationproperties-loading-list-from-yml#45882088

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
