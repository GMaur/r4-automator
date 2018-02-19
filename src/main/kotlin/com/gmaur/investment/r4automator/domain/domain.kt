package com.gmaur.investment.r4automator.domain

import java.math.BigDecimal

interface Asset
data class Fund(val isin: ISIN, val amount: Amount) : Asset
data class Cash(val amount: Amount) : Asset

data class Amount(val value: BigDecimal)
data class ISIN(val value: String)
data class Portfolio(val assets: List<Asset>) {
    fun add(newPortfolio: Portfolio): Portfolio {
        val copy = mutableListOf(*this.assets.toTypedArray())
        copy.addAll(newPortfolio.assets)
        return Portfolio(copy)
    }

}