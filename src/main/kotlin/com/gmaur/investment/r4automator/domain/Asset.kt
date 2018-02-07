package com.gmaur.investment.r4automator.domain

import java.math.BigDecimal

data class Asset(val isin: ISIN, val amount: BigDecimal)