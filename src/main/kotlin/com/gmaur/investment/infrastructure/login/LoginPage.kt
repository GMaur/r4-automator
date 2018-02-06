package com.gmaur.investment.infrastructure.login

import com.gmaur.investment.r4automator.LoginConfiguration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

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
        username(config.username)
        password(config.password)
        nif(config.nif)
        nifBox?.submit()
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
}

