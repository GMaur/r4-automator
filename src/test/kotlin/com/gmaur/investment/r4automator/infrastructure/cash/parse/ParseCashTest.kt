package com.gmaur.investment.r4automator.infrastructure.cash.parse

import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.Cash
import com.gmaur.investment.r4automator.domain.Portfolio
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import org.assertj.core.api.Assertions
import org.junit.Test
import java.math.BigDecimal
import java.nio.file.Paths

class ParseCashTest {

    @Test
    fun `parse the funds from the reduced sample`() {
        val rawHtml = readContents("patrimonio1.html")
        var funds = ParseCash(rawHtml).run()

        Assertions.assertThat(funds).isEqualTo(Portfolio(listOf(Cash(Amount(BigDecimal("9999.99"))))))
    }

    @Test
    fun `parse the funds from sample with long numbers`() {
        val rawHtml = readContents("patrimonio_long-numbers.html")
        var funds = ParseCash(rawHtml).run()

        Assertions.assertThat(funds).isEqualTo(Portfolio(listOf(Cash(Amount(BigDecimal("19999.99"))))))
    }


    private fun readContents(file: String): String {
        val get = inTestClasses(file)
        val rawHtml = FileUtils.readAllLinesAsString(get)
        return rawHtml
    }

    private fun inTestClasses(file: String) = Paths.get("./target/test-classes/cash/samples/" +
            file)
}