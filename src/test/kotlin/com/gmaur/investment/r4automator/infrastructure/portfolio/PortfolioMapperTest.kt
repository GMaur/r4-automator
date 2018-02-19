package com.gmaur.investment.r4automator.infrastructure.portfolio

import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.Fund
import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.parsefunds.FundsObjectMother
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class PortfolioMapperTest {
    @Test
    fun `convert a portfolio`() {
        val mapper = PortfolioMapper()
        val portfolio = Portfolio(listOf(
                Fund(ISIN("LU1"), Amount(BigDecimal.valueOf(1L))),
                Fund(ISIN("LU2"), Amount(BigDecimal.valueOf(2L)))))

        assertThat(mapper.toDTO(portfolio)).isEqualTo(PortfolioDTO(listOf(
                FundDTO(isin = "LU1", price = "1.00"),
                FundDTO(isin = "LU2", price = "2.00"))))
    }

    @Test
    fun `convert another portfolio`() {
        val mapper = PortfolioMapper()
        val portfolio = Portfolio(FundsObjectMother.funds_sample_1())

        assertThat(mapper.toDTO(portfolio)).isEqualTo(PortfolioDTO(listOf(
                FundDTO(isin = "LU1050469367", price = "0.00"),
                FundDTO(isin = "LU1050470373", price = "99999.99"),
                FundDTO(isin = "LU0996177134", price = "0.00"),
                FundDTO(isin = "LU0996182563", price = "1.00"))))
    }
}

