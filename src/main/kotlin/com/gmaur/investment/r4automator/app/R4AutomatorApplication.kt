package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.funds.FundsBuyerPage
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.funds.FundsPage
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.ConsoleTwoFactorAuthenticationProvider
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationPage
import org.openqa.selenium.WebDriver
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal


@SpringBootApplication(scanBasePackages = arrayOf("com.gmaur.investment.r4automator"))
@EnableAutoConfiguration
class R4AutomatorApplication(private val driver: WebDriver, private val fundsConfiguration: FundsConfiguration, private val loginConfiguration: LoginConfiguration) {

    private val `2faProvider` = ConsoleTwoFactorAuthenticationProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)

    @Bean
    fun init() = CommandLineRunner {
        logIn(loginConfiguration)
        enable2FA()
        parseFunds(fundsConfiguration)
        buyFunds()

        this.driver.close()
    }

    private fun buyFunds() {
        var purchaseOrder = FundsBuyerPage.PurchaseOrder(ISIN("LU1050469367"), BigDecimal("250"))
        FundsBuyerPage(driver, UserInteraction).buy(purchaseOrder)
    }

    private fun parseFunds(fundsConfiguration: FundsConfiguration): String {
        return retry { FundsPage(this.driver, fundsConfiguration).parse() }!!
    }

    private fun enable2FA() {
        retry { TwoFactorAuthenticationPage(this.driver).enable(`2faProvider`) }
    }

    private fun logIn(loginConfiguration: LoginConfiguration) {
        retry { LoginPage(this.driver, UserInteraction).login(loginConfiguration) }
    }

    private fun <T> retry(f: () -> T): T? {
        var cont = true
        var result: T? = null
        while (cont) {
            try {
                if (!cont) break
                result = f()
                cont = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }
}

fun main(args: Array<String>) {
    runApplication<R4AutomatorApplication>(*args)
}
