package com.gmaur.investment.r4automator.infrastructure.parsefunds

import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.domain.Fund
import com.gmaur.investment.r4automator.domain.ISIN
import java.math.BigDecimal

object FundsObjectMother {
    fun funds_sample_1(): List<Asset> {
        return arrayListOf(
                Fund(ISIN("LU1050469367"), Amount(BigDecimal("0"))),
                Fund(ISIN("LU1050470373"), Amount(BigDecimal("99999.99"))),
                Fund(ISIN("LU0996177134"), Amount(BigDecimal("0.00"))),
                Fund(ISIN("LU0996182563"), Amount(BigDecimal("1"))))
    }
}