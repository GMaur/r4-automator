package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.domain.Portfolio
import com.gmaur.investment.r4automator.infrastructure.cash.parse.ParseCash
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.parse.ParseFunds
import com.gmaur.investment.r4automator.infrastructure.portfolio.PortfolioDTO
import com.gmaur.investment.r4automator.infrastructure.portfolio.PortfolioMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.nio.file.Files
import java.nio.file.Paths


@RestController
class R4FundsParser {

    @PostMapping("parse")
    fun run(
            @RequestParam("funds", required = true) funds: MultipartFile,
            @RequestParam("cash", required = true) cash: MultipartFile,
            redirectAttributes: RedirectAttributes): PortfolioDTO {

        val tempFileFunds = Files.createTempFile("funds", ".tmp")
        funds.transferTo(tempFileFunds.toFile())

        val tempFileCash = Files.createTempFile("cash", ".tmp")
        cash.transferTo(tempFileCash.toFile())

        val portfolio = parsePortfolio(tempFileFunds.toAbsolutePath().toString(), tempFileCash.toAbsolutePath().toString())
        val dtoMapper = PortfolioMapper()
        return dtoMapper.toDTO(portfolio)
    }

    private fun parsePortfolio(fundsFile: String, cashFile: String): Portfolio {
        val rawFunds = contentsFrom(fundsFile)
        val rawCash = contentsFrom(cashFile)
        return portfolio(rawFunds, rawCash)
    }

    private fun portfolio(rawFunds: String, rawCash: String): Portfolio {
        val portfolio = ParseFunds(rawFunds).run()
        val completePortfolio = portfolio.add(ParseCash(rawCash).run())
        return completePortfolio
    }

    private fun contentsFrom(fundsFile: String) = FileUtils.readAllLinesAsString(Paths.get(fundsFile))

}