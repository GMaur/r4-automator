package com.gmaur.investment.r4automator.infrastructure.funds.buy

import arrow.core.Either
import com.gmaur.investment.r4automator.app.UserInteraction
import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils.newFile
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils.saveFile
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.nio.file.Path

class FundsBuyerPage(private val driver: WebDriver, private val userInteraction: UserInteraction, private val fundsConfiguration: FundsConfiguration) {
    private val fromFundsAccount = By.cssSelector("#fondos-options > td:nth-child(1)")

    fun buy(purchaseOrder: PurchaseOrder): Either<Exception, Path> {
        navigateToTheFundsPage()
        selectFund(purchaseOrder.isin)
        if (impossibleToSelectFromFunds()) {
            val message = "Impossible to fulfill this purchase order" + purchaseOrder
            println(message)
            return Either.left(RuntimeException(message))
        }
        selectFromFundsAccount()
        selectAmount(purchaseOrder.amount)
        acceptAllConditions()
        if (userInteraction.`confirm?`("confirm this operation?")) {
            confirmButton().click()
            val path = newFile()
            savePageSource(path)
            return Either.right(path)
        }
        return Either.left(RuntimeException("the user did not confirm the operation"))
    }

    private fun navigateToTheFundsPage() {
        driver.get(fundsConfiguration.fundsurl)
    }

    private fun impossibleToSelectFromFunds(): Boolean {
        val fromFundsAccount = driver.findElement(fromFundsAccount)
        return fromFundsAccount.getAttribute("class").contains("no-operativa")
    }

    private fun savePageSource(path: Path) {
        saveFile(path, driver.pageSource)
        println("Wrote funds buyer page source to " + path)
    }

    data class PurchaseOrder(val isin: ISIN, val amount: Amount)

    private fun acceptAllConditions() {
        val tables = driver.findElements(By.cssSelector("form > table"))

        val documentation = tables.first()

        val previousPage = driver.windowHandle

        // opens in a new page
        acceptDocumentation(documentation).click()

        goBackTo(previousPage)

        acceptDisclaimers(tables)
    }

    private fun acceptDisclaimers(tables: MutableList<WebElement>) {
        val disclaimers = tables.last()

        //click on all disclaimers
        disclaimers.findElements(By.cssSelector("input[type='checkbox']")).forEach({ it.click() })
    }

    private fun goBackTo(previousPage: String?) {
        driver.switchTo().window(previousPage)
    }

    private fun selectAmount(amount: Amount) {
        typeAmount(amount)
        confirmButton().click()
    }

    private fun typeAmount(amount: Amount) {
        driver.findElement(By.id("IMPORTE_FONDO_1")).sendKeys(amount.value.toString())
    }

    private fun confirmButton() = driver.findElement(By.cssSelector("input[name='B_ENVIAR_ORD']"))

    private fun selectFromFundsAccount() {
        val fromFundsAccount = driver.findElement(fromFundsAccount)
        if (fromFundsAccount.findElement(By.tagName("a")).isEnabled) {
            fromFundsAccount.click()
        }
    }

    private fun selectFund(isin: ISIN) {
        driver.findElement(By.cssSelector("tr[data-isin='" + isin.value + "']")).findElements(By.tagName("a")).last().click()
    }

    private fun acceptDocumentation(table: WebElement) = table.findElements(By.tagName("a")).last()
}