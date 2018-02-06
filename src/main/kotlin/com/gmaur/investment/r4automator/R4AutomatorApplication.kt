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


@SpringBootApplication()
@EnableAutoConfiguration
class R4AutomatorApplication {

    lateinit var driver: WebDriver

    constructor(config: Configuration, loginConfiguration: LoginConfiguration) {
        setup(config)

        doWork(loginConfiguration)

        closeDriver()
    }

    private fun doWork(loginConfiguration: LoginConfiguration) {
        LoginPage(driver).login(loginConfiguration)
    }


    final fun setup(config: Configuration) {
        System.setProperty(config.nameDriver, config.pathDriver + config.exeDriver)
        driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().maximize()
        driver.manage().window().fullscreen()
        driver.get(URI(config.url).toString())
    }

    final fun closeDriver() {
        this.driver.close()
    }

}

fun main(args: Array<String>) {
    runApplication<R4AutomatorApplication>(*args)
}
