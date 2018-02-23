package com.gmaur.investment.r4automator.infrastructure.cash.parse

import com.gmaur.investment.r4automator.infrastructure.cash.CashConfiguration
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import org.openqa.selenium.WebDriver

class CashParserPage(private val driver: WebDriver, private val cashConfiguration: CashConfiguration) {

    fun parse(): String? {
        navigateToThePage()
        return savePageSource()
    }

    private fun navigateToThePage() {
        driver.get(cashConfiguration.cashurl)
    }

    private fun savePageSource(): String? {
        val pageSource = driver.pageSource
        FileUtils.saveTemporaryFile(pageSource)
        return pageSource
    }
}