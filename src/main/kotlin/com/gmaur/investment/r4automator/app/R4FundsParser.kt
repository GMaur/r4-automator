package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.files.FilePickerProvider
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.FilePortfolioRepository
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import java.io.File
import java.nio.file.Paths

class R4FundsParser(private val portfolioRepo: FilePortfolioRepository) {

    private val filePickerProvider: FilePickerProvider = FilePickerProvider.aNew()

    fun run(args: Array<String>) {
        val file = filePickerProvider.request()
        val portfolio = ParseFunds(FileUtils.readAllLinesAsString(Paths.get(file))).run()
        val output = File.createTempFile("temp", "tmp")
        portfolioRepo.save(portfolio, output)
        println("printed to file " + output)
    }

}
fun main(args: Array<String>) {
    R4FundsParser(FilePortfolioRepository()).run(args)
}