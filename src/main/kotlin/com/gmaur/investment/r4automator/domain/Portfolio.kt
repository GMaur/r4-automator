package com.gmaur.investment.r4automator.domain

data class Portfolio(val assets: List<Asset>) {
    fun add(newPortfolio: Portfolio): Portfolio {
        val copy = mutableListOf(*this.assets.toTypedArray())
        copy.addAll(newPortfolio.assets)
        return Portfolio(copy)
    }

}