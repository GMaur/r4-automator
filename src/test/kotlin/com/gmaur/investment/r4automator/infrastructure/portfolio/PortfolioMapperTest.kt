package com.gmaur.investment.r4automator.infrastructure.portfolio

import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.domain.ISIN
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class PortfolioMapperTest {
    @Test
    fun `convert a portfolio`() {
        val mapper = PortfolioMapper()
        val portfolio = Portfolio(listOf(Asset(ISIN("LU1"), Amount(BigDecimal.valueOf(1L)))))

        assertThat(mapper.toDTO(portfolio)).isEqualTo(PortfolioDTO(listOf(AssetDTO(isin = "LU1", price = "1.00"))))
    }
}

