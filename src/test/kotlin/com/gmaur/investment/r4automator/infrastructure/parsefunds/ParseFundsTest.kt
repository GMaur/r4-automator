package com.gmaur.investment.r4automator.infrastructure.parsefunds

import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths

class ParseFundsTest {


    @Test
    fun `parse the funds from the full sample`() {
        val rawHtml = readContents("funds1.html")
        var funds = ParseFunds(rawHtml).run()

        assertThat(funds).containsSequence(funds_sample_1())
    }

    @Test
    fun `parse the funds from the reduced sample`() {
        val rawHtml = readContents("funds2.html")
        var funds = ParseFunds(rawHtml).run()

        assertThat(funds).containsSequence(funds_sample_1())
    }

    private fun funds_sample_1(): List<Asset> {
        return arrayListOf(Asset(ISIN("LU1050469367"), BigDecimal("0")),
                Asset(ISIN("LU1050470373"), BigDecimal("99999.99")),
                Asset(ISIN("LU0996177134"), BigDecimal("0.00")),
                Asset(ISIN("LU0996182563"), BigDecimal("1")))
    }

    private fun readContents(file: String): String {
        val get = Paths.get("./target/test-classes/funds/samples/" +
                file)
        val rawHtml = Files.readAllLines(get).joinToString("")
        return rawHtml
    }
}