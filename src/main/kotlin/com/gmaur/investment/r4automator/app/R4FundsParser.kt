package com.gmaur.investment.r4automator.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.infrastructure.files.FilePickerProvider
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import com.gmaur.investment.r4automator.infrastructure.portfolio.Portfolio
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


class FilePortfolioRepository {

    private var mapper: ObjectMapper = ObjectMapper().registerKotlinModule()


    fun save(portfolio: Portfolio, file: File) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.writeValue(file, portfolio)
    }

}
