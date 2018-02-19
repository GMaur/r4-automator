package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.cash.CashConfiguration
import com.gmaur.investment.r4automator.infrastructure.cash.parse.CashParserPage
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.funds.buy.FundsBuyerPage
import com.gmaur.investment.r4automator.infrastructure.funds.parse.FundsPage
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProvider
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProviderConfiguration
import com.gmaur.investment.r4automator.infrastructure.webdriver.WebDriverUtils
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.math.BigDecimal


@Configuration
@EnableAutoConfiguration
@Import(TwoFactorAuthenticationProviderConfiguration::class)
@ComponentScan(basePackages = arrayOf("com.gmaur.investment.r4automator"))
class R4AutomatorApplication(
        private val driver: WebDriver,
        private val fundsConfiguration: FundsConfiguration,
        private val loginConfiguration: LoginConfiguration,
        private val cashConfiguration: CashConfiguration
) :
        ApplicationRunner {

    @Autowired
    private lateinit var `2faProvider`: TwoFactorAuthenticationProvider

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        logIn(loginConfiguration)
        enable2FA()
        parseFunds(fundsConfiguration)
        parseCash(cashConfiguration)
        buyFunds(fundsConfiguration)

        this.driver.close()
    }


    private fun buyFunds(fundsConfiguration: FundsConfiguration) {
        var purchaseOrder1 = FundsBuyerPage.PurchaseOrder(ISIN("LU1050469367"), BigDecimal("250"))
        protect { FundsBuyerPage(driver, UserInteraction, fundsConfiguration).buy(purchaseOrder1) }
        var purchaseOrder2 = FundsBuyerPage.PurchaseOrder(ISIN("LU0996177134"), BigDecimal("250"))
        protect { FundsBuyerPage(driver, UserInteraction, fundsConfiguration).buy(purchaseOrder2) }
    }

    private fun parseFunds(fundsConfiguration: FundsConfiguration) {
        protect { FundsPage(this.driver, fundsConfiguration).parse() }
    }

    private fun parseCash(cashConfiguration: CashConfiguration) {
        protect { CashParserPage(this.driver, cashConfiguration).parse() }
    }

    private fun enable2FA() {
        protect { TwoFactorAuthenticationPage(this.driver).enable(`2faProvider`) }
    }

    private fun logIn(loginConfiguration: LoginConfiguration) {
        protect { LoginPage(this.driver, UserInteraction).login(loginConfiguration) }
    }

    private fun <T> protect(f: () -> T): T? {
        return WebDriverUtils.protect(driver, f)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(R4AutomatorApplication::class.java, *args)
}