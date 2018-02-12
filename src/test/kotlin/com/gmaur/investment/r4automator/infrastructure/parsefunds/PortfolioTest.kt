package com.gmaur.investment.r4automator.infrastructure.parsefunds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmaur.investment.r4automator.domain.Asset
import org.assertj.core.api.Assertions
import org.junit.Test

data class Portfolio(val assets: List<Asset>)

class PortfolioTest {
    val mapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `serialize & deserialize the assets`() {
        val serialized = mapper.writeValueAsString(FundsObjectMother.funds_sample_1())
        val objects = mapper.readValue<List<Asset>>(serialized)
        Assertions.assertThat(objects).isEqualTo(FundsObjectMother.funds_sample_1())
    }


    @Test
    fun `serialize & deserialize the portfolio`() {
        val serialized = mapper.writeValueAsString(Portfolio(FundsObjectMother.funds_sample_1()))
        val objects = mapper.readValue<Portfolio>(serialized)
        Assertions.assertThat(objects).isEqualTo(Portfolio(FundsObjectMother.funds_sample_1()))
    }

}