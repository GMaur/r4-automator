package com.gmaur.investment.r4automator.infrastructure.login

import com.gmaur.investment.r4automator.app.UserInteraction
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.net.URI

class LoginPage(private val driver: WebDriver, private val userInteraction: UserInteraction) {

    @FindBy(name = "EF_DNI")
    private val nifBox: WebElement? = null

    @FindBy(name = "USUARIO")
    private val usernameBox: WebElement? = null

    @FindBy(name = "PASSWORD")
    private val passwordBox: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun login(config: LoginConfiguration): LoginPage {
        driver.get(URI(config.url).toString())
        username(config.username)
        password(config.password)
        nif(config.nif)
        val shouldLogin = userInteraction.`confirm?`("login?")
        if (shouldLogin) {
            submitForm()
        }
        return this
    }

    fun pageSource(): String {
        return driver.pageSource
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

