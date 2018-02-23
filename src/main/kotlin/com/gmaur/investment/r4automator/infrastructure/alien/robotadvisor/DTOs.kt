package com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor

import java.math.BigDecimal

data class AmountDTO private constructor(val amount: BigDecimal, val currency: String) {
    companion object {
        fun EUR(amount: String): AmountDTO {
            return AmountDTO(BigDecimal(amount), "EUR")
        }
    }

}