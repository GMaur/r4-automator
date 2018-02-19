package com.gmaur.investment.r4automator.infrastructure.portfolio

import com.gmaur.investment.r4automator.domain.Cash
import com.gmaur.investment.r4automator.domain.Fund
import org.springframework.stereotype.Component

@Component
class PortfolioMapper {
    fun toDTO(portfolio: Portfolio): PortfolioDTO {
        return PortfolioDTO(portfolio.assets.map {
            when (it) {
                is Fund -> {
                    FundDTO(isin = it.isin.value, price = it.amount.value.setScale(2).toString())
                }
                is Cash -> {
                    CashDTO(value = it.amount.value.setScale(2).toString())
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        })
    }
}

interface AssetDTO
data class PortfolioDTO(val assets: List<AssetDTO>)
data class CashDTO(val value: String) : AssetDTO {
    val type = "cash"
}

data class FundDTO(val isin: String, val price: String) : AssetDTO {
    val type = "fund"
}
