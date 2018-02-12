package com.gmaur.investment.r4automator.infrastructure.parsefunds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import com.gmaur.investment.r4automator.infrastructure.parsefunds.FundsObjectMother.funds_sample_1
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.nio.file.Paths

class ParseFundsTest {

    val mapper = ObjectMapper().registerKotlinModule()

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

    @Test
    fun `serialize`() {
        val serialized = mapper.writeValueAsString(funds_sample_1())
        val objects = mapper.readValue<List<Asset>>(serialized)
        assertThat(objects).isEqualTo(funds_sample_1())
    }

    private fun readContents(file: String): String {
        val get = inTestClasses(file)
        val rawHtml = FileUtils.readAllLinesAsString(get)
        return rawHtml
    }

    private fun inTestClasses(file: String) = Paths.get("./target/test-classes/funds/samples/" +
            file)
}