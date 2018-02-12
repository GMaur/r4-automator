package com.gmaur.investment.r4automator.infrastructure.funds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.infrastructure.portfolio.Portfolio
import java.io.File

class FilePortfolioRepository {

    private val mapper: ObjectMapper

    constructor() {
        mapper = ObjectMapper().registerKotlinModule()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    fun save(portfolio: Portfolio, file: File) {
        mapper.writeValue(file, portfolio)
    }
}