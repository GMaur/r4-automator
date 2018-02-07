package com.gmaur.investment.r4automator.infrastructure.funds

import com.gmaur.investment.r4automator.domain.ISIN
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.math.BigDecimal

class FundsBuyerPage(private val driver: WebDriver) {
    fun buy(purchaseOrder: PurchaseOrder) {
        selectFund(purchaseOrder.isin)
        selectFromFundsAccount()
        selectAmount(purchaseOrder.amount)
        acceptAllConditions()
        confirm()
    }

    data class PurchaseOrder(val isin: ISIN, val amount: BigDecimal)

    private fun acceptAllConditions() {
        val tables = driver.findElements(By.cssSelector("form > table"))

        val documentation = tables.first()

        val previousPage = driver.windowHandle

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

    private fun selectAmount(amount: BigDecimal) {
        typeAmount(amount)
        confirm()
    }

    private fun typeAmount(amount: BigDecimal) {
        driver.findElement(By.id("IMPORTE_FONDO_1")).sendKeys(amount.toString())
    }

    private fun confirm() = driver.findElement(By.id("B_ENVIAR_ORD"))

    private fun selectFromFundsAccount() {
        driver.findElement(By.cssSelector("#fondos-options > td:nth-child(1)")).click()
    }

    private fun selectFund(isin: ISIN) {
        driver.findElement(By.cssSelector("tr[data-isin='" + isin + "']")).findElements(By.tagName("a")).last().click()
    }

    private fun acceptDocumentation(table: WebElement) = table.findElements(By.tagName("a")).last()
}