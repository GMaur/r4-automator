package com.gmaur.investment.r4automator.app

import arrow.core.Either
import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.ISIN
import com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor.AmountDTO
import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.funds.buy.FundsBuyerPage
import com.gmaur.investment.r4automator.infrastructure.twofactorauth.TwoFactorAuthenticationProviderConfiguration
import com.gmaur.investment.r4automator.infrastructure.webdriver.WebDriverUtils
import org.openqa.selenium.WebDriver
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.nio.file.Path

@Configuration
@Import(TwoFactorAuthenticationProviderConfiguration::class)
@RestController
class R4FundsBuyer(
        private val mapper: DomainMapper
        , private val service: FundsOperationPerformerService
) {

    @PostMapping("/operations")
    fun performOperations(@RequestBody request: OperationsRequest): List<OperationOutcome> {
        val toDomain = mapper.toDomain(request)
        if (toDomain.isLeft()) {
            throw IllegalArgumentException()
        }

        val it = toDomain.get()
        val map = it.values
                .filter { it is FundPurchase }
                .map { it as FundPurchase }
                .map { Pair(it, service.mock(it)) }

        val result = map.map { (a, b) ->
            OperationOutcome(a, b.map { FileUtils.readAllLinesAsString(it) })
        }
        return result
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException() {
    }
}

data class OperationOutcome(val operation: Operation, val result: Either<Exception, String>)

@Component
open class FundsOperationPerformerService(
        private val driver: WebDriver,
        private val fundsConfiguration: FundsConfiguration) {

    fun perform(fund: FundPurchase): Either<Exception, Path> {
        return listOf(fund)
                .map { FundsBuyerPage.PurchaseOrder(it.isin, it.amount) }
                .map { protect { FundsBuyerPage(driver, UserInteraction, fundsConfiguration).buy(it) }!! }
                .first()
    }

    private fun <T> protect(f: () -> T): T? {
        return WebDriverUtils.protect(driver, f)
    }

    fun mock(it: FundPurchase): Either<Exception, Path> {
        println(it)
        return Either.left(IllegalArgumentException())
    }
}

@Component
class DomainMapper {
    fun toDomain(dtos: OperationsRequest): Either<Exception, Operations> {
        return try {
            Either.right(Operations(dtos.operations.map {
                when (it) {
                    is FundOperationRequest -> {
                        FundPurchase.aNew(it.asset.isin, it.amount.amount.toString())
                    }
                    else -> {
                        throw IllegalArgumentException("x")
                    }

                }
            }))
        } catch (e: Exception) {
            Either.left(Exception("x"))
        }
    }

}

data class Operations(val values: List<Operation>)

interface Operation

data class FundPurchase private constructor(val isin: ISIN, val amount: Amount) : Operation {
    companion object {
        fun aNew(isin: String, value: String): FundPurchase {
            return FundPurchase(ISIN(isin), Amount(BigDecimal(value)))
        }
    }
}

data class OperationsRequest(val operations: List<OperationRequest>)
interface OperationRequest
data class FundOperationRequest(val type: String, val asset: FundDefinitionDTO, val amount: AmountDTO) : OperationRequest
data class FundDefinitionDTO(val isin: String)