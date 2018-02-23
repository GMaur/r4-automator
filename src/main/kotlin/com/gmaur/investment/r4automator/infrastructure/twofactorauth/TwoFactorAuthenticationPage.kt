package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

class TwoFactorAuthenticationPage(private val driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }

    fun enable(provider: TwoFactorAuthenticationProvider): TwoFactorAuthenticationPage {
        val twofaImage = fun(): String { return driver.findElement(By.id("2fa")).findElement(By.tagName("img")).getAttribute("src") }
        if (twofaImage().contains("-consultiva.png")) {
            open2FAModal()
            val code = provider.request()
            insertSecondFactor(code)
            submitForm()
            println("new url = " + twofaImage())
            if (twofaImage().contains("-operativa.png")) {
                println("2FA has been activated")
            } else {
                println("2FA could not be activated")
            }
        } else {
            println("2FA is apparently enabled already")
        }
        return this
    }

    fun pageSource(): String {
        return driver.pageSource
    }

    private fun insertSecondFactor(password: String) {
        driver.findElement(By.id("COD")).sendKeys(password)
    }

    private fun open2FAModal() {
        driver.findElement(By.id("2fa")).click()
    }

    private fun submitForm() {
        driver.findElements(By.cssSelector("#modal2fa-temp input"))[1].click()
    }

}

