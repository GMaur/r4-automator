package com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor

import com.fasterxml.jackson.module.kotlin.readValue
import com.gmaur.investment.r4automator.app.FundOperationRequest
import com.gmaur.investment.r4automator.app.OperationsRequest
import com.gmaur.investment.r4automator.infrastructure.JSONMapper
import org.assertj.core.api.SoftAssertions
import org.junit.Test

class OperationDeserializerShould {
    @Test
    fun `parse from a valid JSON`() {
        val mapper = JSONMapper.aNew()
        val operationsJSON = RobotAdvisorResponseObjectMother.valid()

        val dto = mapper.readValue<OperationsRequest>(operationsJSON)
        
        val softly = SoftAssertions()
        softly.assertThat(dto.operations).hasSize(2)

        val first = dto.operations[0] as FundOperationRequest
        softly.assertThat(first.type).isEqualTo("purchase")
        softly.assertThat(first.asset.isin).isEqualTo("LU0")
        softly.assertThat(first.amount).isEqualTo(AmountDTO.EUR("0.00"))

        val second = dto.operations[1] as FundOperationRequest
        softly.assertThat(second.type).isEqualTo("purchase")
        softly.assertThat(second.asset.isin).isEqualTo("LU1")
        softly.assertThat(second.amount).isEqualTo(AmountDTO.EUR("1.00"))

        softly.assertAll()
    }
}

