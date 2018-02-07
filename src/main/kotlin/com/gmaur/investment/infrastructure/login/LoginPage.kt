package com.gmaur.investment.infrastructure.login

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.net.URI

class LoginPage(private val driver: WebDriver) {

    @FindBy(name = "EF_DNI")
    private val nifBox: WebElement? = null

    @FindBy(name = "USUARIO")
    private val usernameBox: WebElement? = null

    @FindBy(name = "PASSWORD")
    private val passwordBox: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun login(config: LoginConfiguration) {
        driver.get(URI(config.url).toString())
        username(config.username)
        password(config.password)
        nif(config.nif)
        submitForm()
    }

    private fun nif(value: String) {
        nifBox?.sendKeys(value)
    }

    private fun password(value: String) {
        passwordBox?.sendKeys(value)
    }

    private fun username(value: String) {
        usernameBox?.sendKeys(value)
    }

    private fun submitForm() {
        nifBox?.submit()
    }
}

