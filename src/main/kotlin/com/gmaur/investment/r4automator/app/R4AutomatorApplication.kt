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
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal

@SpringBootApplication(scanBasePackages = arrayOf("com.gmaur.investment.r4automator"))
class R4AutomatorApplication(private val driver: WebDriver, private val fundsConfiguration: FundsConfiguration, private val loginConfiguration: LoginConfiguration) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg strings: String) {
        logIn(loginConfiguration)
        enable2FA()
        parseFunds(fundsConfiguration)
        buyFunds()

        this.driver.close()
    }

    private val `2faProvider` = ConsoleTwoFactorAuthenticationProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)

    private fun buyFunds() {
        var purchaseOrder = FundsBuyerPage.PurchaseOrder(ISIN("LU1050469367"), BigDecimal("250"))
        FundsBuyerPage(driver, UserInteraction).buy(purchaseOrder)
    }

    private fun parseFunds(fundsConfiguration: FundsConfiguration): String {
        return protect { FundsPage(this.driver, fundsConfiguration).parse() }!!
    }

    private fun enable2FA() {
        protect { TwoFactorAuthenticationPage(this.driver).enable(`2faProvider`) }
    }

    private fun logIn(loginConfiguration: LoginConfiguration) {
        protect { LoginPage(this.driver, UserInteraction).login(loginConfiguration) }
    }

    private fun <T> protect(f: () -> T): T? {
        try {
            return f()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(R4AutomatorApplication::class.java, *args)
}