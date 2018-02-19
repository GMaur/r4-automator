package com.gmaur.investment.r4automator.infrastructure.funds

import com.gmaur.investment.r4automator.domain.Portfolio
import com.gmaur.investment.r4automator.infrastructure.parsefunds.FundsObjectMother.funds_sample_1
import com.gmaur.investment.r4automator.infrastructure.portfolio.FilePortfolioRepository
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions
import org.junit.Test
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

class FilePortfolioRepositoryTest {
    @Test
    fun `persist to file`() {
        val tempFile = File.createTempFile("tempfile", ".tmp")
        val repo = FilePortfolioRepository()

        repo.save(Portfolio(funds_sample_1()), tempFile)
        println("saved portfolio to " + tempFile.toPath().toAbsolutePath())

        val softly = SoftAssertions()
        val contentOf = Assertions.contentOf(tempFile)
        softly.assertThat(contentOf).isNotNull()
        softly.assertThat(contentOf.length).isGreaterThan(100 /*bytes*/)
        val fileType = detectFileType(tempFile)
        softly.assertThat(arrayOf(fileType)).containsAnyOf("text/plain", "application/json").hasSize(1)
        softly.assertThat(tempFile).isFile()
        softly.assertThat(tempFile).exists()
        softly.assertAll()
    }

    // https://stackoverflow.com/questions/51438/getting-a-files-mime-type-in-java
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