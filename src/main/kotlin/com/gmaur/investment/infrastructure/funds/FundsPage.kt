package com.gmaur.investment.infrastructure.funds

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class FundsPage(private val driver: WebDriver, private val config: FundsConfiguration) {
    init {
        PageFactory.initElements(driver, this)
    }

    fun parse(): String {
        driver.get(URI(config.url).toString())
        val pageSource = driver.pageSource
        Files.write(Paths.get("/tmp/out" + Math.abs(Random().nextLong()) + ".html"), pageSource.toByteArray())
        return pageSource
    }
}