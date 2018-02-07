package com.gmaur.investment.infrastructure.parsefunds

import com.gmaur.investment.infrastructure.funds.ParseFunds
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths

class ParseFundsTest {


    @Test
    fun `parse the funds from the full sample`() {
        val get = Paths.get("./target/test-classes/funds/samples/funds1.html")
        val rawHtml = Files.readAllLines(get).joinToString("")
        var funds = ParseFunds(rawHtml).run()

        assertThat(funds).containsSequence(
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU1050469367"), BigDecimal("0")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU1050470373"), BigDecimal("99999.99")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU0996177134"), BigDecimal("0.00")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU0996182563"), BigDecimal("1"))
        )
    }

    @Test
    fun `parse the funds from the reduced sample`() {
        val get = Paths.get("./target/test-classes/funds/samples/funds2.html")
        val rawHtml = Files.readAllLines(get).joinToString("")
        var funds = ParseFunds(rawHtml).run()

        assertThat(funds).containsSequence(
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU1050469367"), BigDecimal("0")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU1050470373"), BigDecimal("99999.99")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU0996177134"), BigDecimal("0.00")),
                ParseFunds.AssetDTO(ParseFunds.ISIN("LU0996182563"), BigDecimal("1"))
        )
    }
}