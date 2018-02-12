package com.gmaur.investment.r4automator.app

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
        val file = filePickerProvider.request()
        val portfolio = parsePortfolio(file)
        val output = savePortfolio(portfolio)
        println("printed to file " + output)
    }

    private fun savePortfolio(portfolio: Portfolio): File? {
        val output = File.createTempFile("temp", "tmp")
        portfolioRepo.save(portfolio, output)
        return output
    }

    private fun parsePortfolio(file: String): Portfolio {
        val portfolio = ParseFunds(FileUtils.readAllLinesAsString(Paths.get(file))).run()
        return portfolio
    }

}
fun main(args: Array<String>) {
    R4FundsParser(FilePortfolioRepository()).run(args)
}