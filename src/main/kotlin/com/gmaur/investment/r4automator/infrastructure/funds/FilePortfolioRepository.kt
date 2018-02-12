package com.gmaur.investment.r4automator.infrastructure.funds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.infrastructure.portfolio.Portfolio
import java.io.File

class FilePortfolioRepository {
    private var mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    fun save(portfolio: Portfolio, file: File) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.writeValue(file, portfolio)
    }
}