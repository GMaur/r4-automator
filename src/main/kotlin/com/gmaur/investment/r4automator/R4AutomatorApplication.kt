package com.gmaur.investment.r4automator

import com.gmaur.investment.infrastructure.login.LoginConfiguration
import com.gmaur.investment.infrastructure.login.LoginPage
import org.openqa.selenium.WebDriver
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = arrayOf("com.gmaur.investment.infrastructure"))
@EnableAutoConfiguration
class R4AutomatorApplication {

    var driver: WebDriver

    constructor(driver: WebDriver, loginConfiguration: LoginConfiguration) {
        this.driver = driver

        doWork(loginConfiguration)

        this.driver.close()
    }

    private fun doWork(loginConfiguration: LoginConfiguration) {
        LoginPage(driver).login(loginConfiguration)
    }

}

fun main(args: Array<String>) {
    runApplication<R4AutomatorApplication>(*args)
}
