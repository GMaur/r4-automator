package com.gmaur.investment.r4automator.infrastructure.portfolio

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.domain.Portfolio
import java.io.File

class FilePortfolioRepository {

    private val mapper: ObjectMapper = ObjectMapper().registerKotlinModule()
    private val dtoMapper = PortfolioMapper()

    fun save(portfolio: Portfolio, file: File) {
        mapper.writeValue(file, dtoMapper.toDTO(portfolio))
    }

    init {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }
}