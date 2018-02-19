package com.gmaur.investment.r4automator.infrastructure.funds.parse

import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import java.net.URI

class FundsPage(private val driver: WebDriver, private val config: FundsConfiguration) {
    init {
        PageFactory.initElements(driver, this)
    }

    fun parse(): String {
        driver.get(URI(config.fundsurl).toString())
        FileUtils.saveTemporaryFile(driver.pageSource)
        return driver.pageSource
    }
}