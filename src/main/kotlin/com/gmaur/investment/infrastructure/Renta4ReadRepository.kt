package com.gmaur.investment.infrastructure

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class HomePage(private val driver: WebDriver) {

    @FindBy(id = "masthead-search-term")
    private val searchBox: WebElement? = null

    @FindBy(id = "search-btn")
    private val searchButton: WebElement? = null

    @FindBy(xpath = ".//*[@id='section-list-201801']/li[1]/div/div[1]/div/p")
    private val numResult: WebElement? = null

    init {
        PageFactory.initElements(driver, this)
    }

    fun searchVideo(video: String) {
        searchBox?.sendKeys(video)
        searchButton?.click()
    }
}

