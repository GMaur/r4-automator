package com.gmaur.investment.infrastructure.twofactorauth

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

class TwoFactorAuthenticationPage(private val driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }

    fun enable(provider: TwoFactorAuthenticationProvider) {
        open2FAModal()
        val code = provider.request()
        insertSecondFactor(code)
        submitForm()
        //assert color is green
        // driver.findElement(By.id("2fa")).findElement(By.tagName("img")).getAttribute("src")
        //assert to "https://www.r4.com/recursos/images/mw-images/icon-2fa-operativa.png-operativa.png"

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

