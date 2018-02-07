package com.gmaur.investment.r4automator.app

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


@SpringBootApplication(scanBasePackages = arrayOf("com.gmaur.investment.r4automator"))
@EnableAutoConfiguration
class R4AutomatorApplication(private val driver: WebDriver, private val fundsConfiguration: FundsConfiguration, private val loginConfiguration: LoginConfiguration) {

    private val `2faProvider` = ConsoleTwoFactorAuthenticationProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)

    @Bean
    fun init() = CommandLineRunner {
        logIn(loginConfiguration)
        enable2FA()
        parseFunds(fundsConfiguration)

        //select fund
        // driver.findElement(By.cssSelector("tr[data-isin='LU1050469367']")).findElements(By.tagName("a")).last().click()

        // purchase from the cash account
        // driver.findElement(By.cssSelector("#fondos-options > td:nth-child(1)")).click()

        // type the amount
        // driver.findElement(By.id("IMPORTE_FONDO_1")).sendKeys("250")

        // confirm purhcase
        // driver.findElement(By.id("B_ENVIAR_ORD")).click()

        // find the tables to interact to
        // val tables = driver.findElements(By.cssSelector("form > table"))

        // val documentation = tables.first()

//                val base = driver.windowHandle

        // click on the last element to open the new page - accepts the documentation
        // driver.findElements(By.cssSelector("form > table")).first().findElements(By.tagName("a")).last().click()

//        driver.switchTo().window(base)

        // val disclaimers = tables.last()
        //click on all disclaimers
        //driver.findElements(By.cssSelector("form > table")).last().findElements(By.cssSelector("input[type='checkbox']")).forEach({it.click()})

        // confirm purhcase
        // driver.findElement(By.id("B_ENVIAR_ORD")).click()

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
