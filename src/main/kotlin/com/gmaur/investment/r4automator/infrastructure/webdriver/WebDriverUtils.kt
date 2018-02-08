package com.gmaur.investment.r4automator.infrastructure.webdriver

import com.google.common.io.Files
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import java.nio.file.Paths
import java.util.*

object WebDriverUtils {
    fun <T> protect(driver: WebDriver, f: () -> T): T? {
        try {
            return f()
        } catch (e: Exception) {
            val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
            val currentFailure = Math.abs(Random().nextLong())
            Files.copy(scrFile, File(Paths.get("/tmp/failure_" + currentFailure + "_screenshot.png").toUri()))
            e.printStackTrace()
            return null
        }
    }

}