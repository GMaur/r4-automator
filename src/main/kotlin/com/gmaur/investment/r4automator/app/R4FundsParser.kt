package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.cash.parse.ParseCash
import com.gmaur.investment.r4automator.infrastructure.files.FilePickerProvider
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.FilePortfolioRepository
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import com.gmaur.investment.r4automator.infrastructure.portfolio.Portfolio
import java.io.File
import java.nio.file.Paths

class R4FundsParser(private val portfolioRepo: FilePortfolioRepository) {

    private val filePickerProvider: FilePickerProvider = FilePickerProvider.aNew()

    fun run(args: Array<String>) {
        val fundsFile = filePickerProvider.request("funds")
        val cashFile = filePickerProvider.request("cash")
        val portfolio = parsePortfolio(fundsFile, cashFile)
        val output = savePortfolio(portfolio)
        println("printed to file " + output)
    }

    private fun savePortfolio(portfolio: Portfolio): File? {
        val output = File.createTempFile("temp", "tmp")
        portfolioRepo.save(portfolio, output)
        return output
    }

    private fun parsePortfolio(fundsFile: String, cashFile: String): Portfolio {
        val portfolio = ParseFunds(contentsFrom(fundsFile)).run()
        val completePortfolio = portfolio.add(ParseCash(contentsFrom(cashFile)).run())
        return completePortfolio
    }

    private fun contentsFrom(fundsFile: String) = FileUtils.readAllLinesAsString(Paths.get(fundsFile))

}

fun main(args: Array<String>) {
    R4FundsParser(FilePortfolioRepository()).run(args)
}