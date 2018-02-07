package com.gmaur.investment.r4automator.infrastructure.funds

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
        val path = Paths.get("/tmp/out_" + Math.abs(Random().nextLong()) + ".html")
        Files.write(path, pageSource.toByteArray())
        println("Wrote temporal page source to " + path)
        return pageSource
    }
}