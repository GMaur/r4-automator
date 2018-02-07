package com.gmaur.investment.r4automator

import com.gmaur.investment.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.infrastructure.funds.FundsPage
import com.gmaur.investment.infrastructure.login.LoginConfiguration
import com.gmaur.investment.infrastructure.login.LoginPage
import com.gmaur.investment.infrastructure.twofactorauth.ConsoleTwoFactorAuthenticationProvider
import com.gmaur.investment.infrastructure.twofactorauth.TwoFactorAuthenticationPage
import org.openqa.selenium.WebDriver
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.BufferedReader
import java.io.InputStreamReader


@SpringBootApplication(scanBasePackages = arrayOf("com.gmaur.investment.infrastructure"))
@EnableAutoConfiguration
class R4AutomatorApplication {

    var driver: WebDriver

    private val `2faProvider` = ConsoleTwoFactorAuthenticationProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)

    constructor(driver: WebDriver, fundsConfiguration: FundsConfiguration, loginConfiguration: LoginConfiguration) {
        this.driver = driver

        logIn(loginConfiguration)
        enable2FA()
        parseFunds(fundsConfiguration)
        
        this.driver.close()
    }

    private fun parseFunds(fundsConfiguration: FundsConfiguration): String {
        var funds = FundsPage(this.driver, fundsConfiguration).parse()
        return funds
    }

    private fun enable2FA() {
        TwoFactorAuthenticationPage(this.driver).enable(`2faProvider`)
    }

    private fun logIn(loginConfiguration: LoginConfiguration) {
        LoginPage(this.driver).login(loginConfiguration)
    }

}

fun main(args: Array<String>) {
    runApplication<R4AutomatorApplication>(*args)
}
