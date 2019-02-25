package com.gmaur.investment.r4automator.infrastructure.twofactorauth

import org.openqa.selenium.*
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


class TwoFactorAuthenticationPage(private val driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }

    private fun source(): WebElement = driver.findElement(By.cssSelector("div.right-items")).findElements(By.cssSelector("a.nav-link.dropdown-toggle"))[1]!!

    fun enable(provider: TwoFactorAuthenticationProvider): TwoFactorAuthenticationPage {
        if (section(source()).isDisabled()) {
            open2FAModal()
            val code = provider.request()
            insertSecondFactor(code)
            submitForm()
            closePossibleModals()
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

    private fun closePossibleModals() {
        try {
            driver.findElements(By.cssSelector("div.r4helpwizard-close")).map { it.click() }
        } catch (e:Exception){

        }
        try {
            driver.findElements(By.cssSelector("div.r4icon-close"))[0].click()
        } catch (e:Exception){

        }
    }

    fun has2FAEnabled(): Boolean {
        return section(source()).hasBeenEnabled()
    }

    class section(private val imageURL: WebElement) {
        fun isDisabled(): Boolean {
            return try {
                imageURL.findElement(By.cssSelector(".consultiva"))
                true
            } catch (e: Exception) {
                false
            }
        }

        fun hasBeenEnabled(): Boolean {
            return try {
                imageURL.findElement(By.cssSelector(".operativa"))
                true
            } catch (e: Exception) {
                false
            }
        }
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
            val js: JavascriptExecutor = driver as JavascriptExecutor
            js.executeScript("modalOpen()")
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

