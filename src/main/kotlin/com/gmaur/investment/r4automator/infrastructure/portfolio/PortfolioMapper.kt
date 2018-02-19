package com.gmaur.investment.r4automator.infrastructure.portfolio

class PortfolioMapper {
    fun toDTO(portfolio: Portfolio): PortfolioDTO {
        return PortfolioDTO(portfolio.assets.map { AssetDTO(isin = it.isin.value, price = it.amount.value.setScale(2).toString()) })
    }

}

data class PortfolioDTO(val listOf: List<AssetDTO>)
data class AssetDTO(val isin: String, val price: String)