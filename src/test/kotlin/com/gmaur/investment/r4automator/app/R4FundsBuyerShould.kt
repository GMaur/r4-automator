package com.gmaur.investment.r4automator.app

import arrow.core.Either
import com.gmaur.investment.r4automator.UtilsTest.any
import com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor.AmountDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.nio.file.Paths


class R4FundsBuyerShould {
    @Test
    fun `interact with the service`() {
        val service = mock(FundsOperationPerformerService::class.java)
        `when`(service.perform(any()))
                .thenReturn(Either.right(Paths.get("/etc/hosts")))
                .thenReturn(Either.left(IllegalArgumentException()))


        val sut = R4FundsBuyer(DomainMapper(), service)

        val result = sut.performOperations(OperationsRequest(listOf(
                FundOperationRequest("purchase", FundDefinitionDTO("LU21"), AmountDTO.EUR("21.00")),
                FundOperationRequest("purchase", FundDefinitionDTO("LU22"), AmountDTO.EUR("22.00")),
                FundOperationRequest("purchase", FundDefinitionDTO("LU23"), AmountDTO.EUR("23.00")))))

        assertThat(result).hasSize(3)
        for (entry in result) {
            println(entry)
        }
    }
}