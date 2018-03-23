package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


class TwoFactorAuthenticationPage(private val driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }

    private fun source(): String = driver.findElement(By.id("2fa")).findElement(By.tagName("img")).getAttribute("src")

    fun enable(provider: TwoFactorAuthenticationProvider): TwoFactorAuthenticationPage {
        if (section(source()).isDisabled()) {
            open2FAModal()
            val code = provider.request()
            insertSecondFactor(code)
            submitForm()
            println("new url = " + source())
            if (section(source()).hasBeenEnabled()) {
                println("2FA has been activated")
            } else {
                println("2FA could not be activated")
            }
        } else {
            println("2FA is apparently enabled already")
        }
        return this
    }

    fun has2FAEnabled(): Boolean {
        return section(source()).hasBeenEnabled()
    }

    class section(private val imageURL: String) {
        fun isDisabled() = imageURL.contains("-consultiva.png")
        fun hasBeenEnabled() = imageURL.contains("-operativa.png")
    }

    fun pageSource(): String {
        return driver.pageSource
    }

    private fun insertSecondFactor(password: String) {
        driver.findElement(By.id("COD")).clear()
        driver.findElement(By.id("COD")).sendKeys(password)
    }

    private fun open2FAModal() {
        try {
            driver.findElement(By.id("2fa")).click()
        } catch (e: WebDriverException) {
        }
    }

    private fun submitForm() {
        val cssSelector = By.cssSelector("#modal2fa-temp input.r4button-primary")
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(cssSelector))
        val js: JavascriptExecutor = driver as JavascriptExecutor
        val findElement = driver.findElement(cssSelector)
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", findElement)
        js.executeScript("validarCod();", findElement)
    }

}

