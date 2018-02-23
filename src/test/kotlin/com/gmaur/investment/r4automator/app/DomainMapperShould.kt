package com.gmaur.investment.r4automator.app

import com.fasterxml.jackson.module.kotlin.readValue
import com.gmaur.investment.r4automator.infrastructure.JSONMapper
import com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor.RobotAdvisorResponseObjectMother
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.Test

class DomainMapperShould {
    @Test
    fun `map a json to a domain`() {
        val payload = RobotAdvisorResponseObjectMother.valid()
        val dtos = JSONMapper.aNew().readValue<OperationsRequest>(payload)

        val domain = DomainMapper().toDomain(dtos)

        val softly = SoftAssertions()
        assertThat(domain.isRight())
        domain.map { domain ->
            assertThat(domain.values).hasSize(2)

            assertThat(domain.values.first()).isEqualTo(FundPurchase.aNew("LU0", "0.00"))
            assertThat(domain.values[1]).isEqualTo(FundPurchase.aNew("LU1", "1.00"))
        }
        softly.assertAll()
    }

    @Test
    fun `map an empty list`() {
        val dtos = OperationsRequest(listOf())

        val domain = DomainMapper().toDomain(dtos)

        val softly = SoftAssertions()
        assertThat(domain.isRight())
        domain.map { domain ->
            assertThat(domain.values).hasSize(0)
        }
        softly.assertAll()
    }

}