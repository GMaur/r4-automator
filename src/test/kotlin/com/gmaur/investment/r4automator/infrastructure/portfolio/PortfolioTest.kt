package com.gmaur.investment.r4automator.infrastructure.portfolio

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.infrastructure.funds.FilePortfolioRepository
import com.gmaur.investment.r4automator.infrastructure.parsefunds.FundsObjectMother
import com.gmaur.investment.r4automator.infrastructure.parsefunds.FundsObjectMother.funds_sample_1
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.contentOf
import org.assertj.core.api.SoftAssertions
import org.junit.Test
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream


class PortfolioTest {
    val mapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `serialize & deserialize the assets`() {
        val serialized = mapper.writeValueAsString(FundsObjectMother.funds_sample_1())
        val objects = mapper.readValue<List<Asset>>(serialized)
        Assertions.assertThat(objects).isEqualTo(FundsObjectMother.funds_sample_1())
    }


    @Test
    fun `serialize & deserialize the portfolio`() {
        val serialized = mapper.writeValueAsString(Portfolio(FundsObjectMother.funds_sample_1()))
        val objects = mapper.readValue<Portfolio>(serialized)
        Assertions.assertThat(objects).isEqualTo(Portfolio(FundsObjectMother.funds_sample_1()))
    }

    @Test
    fun `serialize to file`() {
        val tempFile = File.createTempFile("tempfile", ".tmp")
        val repo = FilePortfolioRepository()

        repo.save(Portfolio(funds_sample_1()), tempFile)

        val softly = SoftAssertions()
        val contentOf = contentOf(tempFile)
        softly.assertThat(contentOf).isNotNull()
        softly.assertThat(contentOf.length).isGreaterThan(100 /*bytes*/)
        val fileType = detectFileType(tempFile)
        softly.assertThat(arrayOf(fileType)).containsAnyOf("text/plain", "application/json").hasSize(1)
        softly.assertThat(tempFile).isFile()
        softly.assertThat(tempFile).exists()
        softly.assertAll()
    }

    private fun detectFileType(tempFile: File?): String {
        val fileType = FileInputStream(tempFile).use {
            BufferedInputStream(it).use { bis ->
                val parser = AutoDetectParser()
                val detector = parser.detector
                val metadata = Metadata()
                // metadata.add(Metadata.RESOURCE_NAME_KEY, "file1.json")
                // this introduces too much bias: it is only necessary to specify the json
                val mediaType = detector.detect(bis, metadata)
                mediaType.toString()
            }
        }
        return fileType
    }

}