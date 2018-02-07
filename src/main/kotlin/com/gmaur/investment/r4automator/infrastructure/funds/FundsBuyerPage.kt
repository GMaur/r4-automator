package com.gmaur.investment.r4automator.infrastructure.funds

import org.openqa.selenium.WebDriver

class FundsBuyerPage(private val driver: WebDriver) {
    fun buy() {
        //select fund
        // driver.findElement(By.cssSelector("tr[data-isin='LU1050469367']")).findElements(By.tagName("a")).last().click()

        // purchase from the cash account
        // driver.findElement(By.cssSelector("#fondos-options > td:nth-child(1)")).click()

        // type the amount
        // driver.findElement(By.id("IMPORTE_FONDO_1")).sendKeys("250")

        // confirm purhcase
        // driver.findElement(By.id("B_ENVIAR_ORD")).click()

        // find the tables to interact to
        // val tables = driver.findElements(By.cssSelector("form > table"))

        // val documentation = tables.first()

//                val base = driver.windowHandle

        // click on the last element to open the new page - accepts the documentation
        // driver.findElements(By.cssSelector("form > table")).first().findElements(By.tagName("a")).last().click()

//        driver.switchTo().window(base)

        // val disclaimers = tables.last()
        //click on all disclaimers
        //driver.findElements(By.cssSelector("form > table")).last().findElements(By.cssSelector("input[type='checkbox']")).forEach({it.click()})

        // confirm purhcase
        // driver.findElement(By.id("B_ENVIAR_ORD")).click()
    }
}