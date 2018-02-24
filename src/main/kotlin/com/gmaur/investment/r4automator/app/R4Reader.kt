package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.cash.CashConfiguration
import com.gmaur.investment.r4automator.infrastructure.cash.parse.CashParserPage
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.funds.parse.FundsPage
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProvider
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProviderConfiguration
import com.gmaur.investment.r4automator.infrastructure.webdriver.WebDriverUtils
import org.openqa.selenium.WebDriver
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Configuration
@Import(TwoFactorAuthenticationProviderConfiguration::class)
@RestController
class R4Reader(
        private val driver: WebDriver,
        private val fundsConfiguration: FundsConfiguration,
        private val loginConfiguration: LoginConfiguration,
        private val cashConfiguration: CashConfiguration) {

    @PostMapping("login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        // TODO AGB: find a safe way to store + send credentials - for the moment in the private.properties
//        loginConfiguration.nif = loginRequest.nif
//        loginConfiguration.username = loginRequest.username
//        loginConfiguration.password = loginRequest.password
        val pageSource = logIn(loginConfiguration)
        return ResponseEntity.ok(pageSource)
    }

    @PostMapping("2fa")
    fun twofa(@RequestBody secondFactor: TwoFactorAuthRequest): ResponseEntity<Any>? {
        val success = enable2FA(secondFactor.otp)
        return if (success) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("scrapes/funds")
    fun scrapeFunds(): ResponseEntity<String>? {
        return ResponseEntity.ok(parseFunds(fundsConfiguration))
    }

    @GetMapping("scrapes/cash")
    fun scrapeCash(): ResponseEntity<String>? {
        return ResponseEntity.ok(parseCash(cashConfiguration))
    }

    private fun parseFunds(fundsConfiguration: FundsConfiguration): String {
        return protect { FundsPage(this.driver, fundsConfiguration).parse() }!!
    }

    private fun parseCash(cashConfiguration: CashConfiguration): String {
        return protect { CashParserPage(this.driver, cashConfiguration).parse() }!!
    }

    private fun enable2FA(secondFactor: String): Boolean {
        return protect {
            TwoFactorAuthenticationPage(this.driver).enable(object : TwoFactorAuthenticationProvider {
                override fun request(): String {
                    return secondFactor
                }
            }).has2FAEnabled()
        }!!
    }

    private fun logIn(loginConfiguration: LoginConfiguration): String {
        return protect {
            val loginPage = LoginPage(this.driver, UserInteraction)
            val afterLogin = loginPage.login(loginConfiguration)
            afterLogin.pageSource()
        }!!
    }

    private fun <T> protect(f: () -> T): T? {
        return WebDriverUtils.protect(driver, f)
    }

    data class TwoFactorAuthRequest(val otp: String)

    data class LoginRequest(val username: String, val password: String, val nif: String)
}