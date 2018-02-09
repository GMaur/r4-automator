package com.gmaur.investment.r4automator.infrastructure.webdriver

import com.google.common.io.Files
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import java.io.PrintWriter
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

object WebDriverUtils {
    fun <T> protect(driver: WebDriver, f: () -> T): T? {
        try {
            return f()
        } catch (e: Exception) {
            handleFailure(driver, e)
            printStackTrace(e)
            return null
        }
    }

    private fun handleFailure(driver: WebDriver, e: Exception) {
        val failure = Failure.aNew()
        takeAScreenshot(driver, failure)
        keepStackTrace(e, failure)
        keepPageSource(failure, driver)
    }

    private fun printStackTrace(e: Exception) {
        e.printStackTrace()
    }

    private fun keepPageSource(failure: Failure, driver: WebDriver) {
        java.nio.file.Files.write(failure.page, driver.pageSource.toByteArray())
    }

    private fun keepStackTrace(e: Exception, failure: Failure) {
        e.printStackTrace(PrintWriter(File(failure.stacktrace.toUri())))
    }

    private fun takeAScreenshot(driver: WebDriver, failure: Failure) {
        val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        Files.copy(scrFile, File(failure.screenshot.toUri()))
    }


    data class Failure(val screenshot: Path, val stacktrace: Path, val page: Path) {
        companion object {
            fun aNew(): Failure {
                val failureId = Math.abs(Random().nextLong())

                val screenshot = Paths.get("/tmp/failure_" + failureId + "_screenshot.png")
                val stacktrace = Paths.get("/tmp/failure_" + failureId + "_stacktrace.txt")
                val page = Paths.get("/tmp/failure_" + failureId + "_page.html")

                return Failure(screenshot, stacktrace, page)
            }
        }
    }

}