package com.gmaur.investment.r4automator.app

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.gmaur.investment.r4automator.infrastructure.JSONMapper
import com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor.RobotAdvisorResponseObjectMother
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Path


@RunWith(SpringRunner::class)
@EnableAutoConfiguration()
@ContextConfiguration(classes = [FundsConfiguration::class, LoginConfiguration::class, FakeOperationPerformerService::class])
@ComponentScan(basePackages = ["com.gmaur.investment.r4automator"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class R4FundsBuyerFeature {
    @LocalServerPort
    var port: Int? = null

    private val objectMapper: ObjectMapper = JSONMapper.aNew()

    @Before
    fun setUp() {
        FuelManager.instance.basePath = "http://localhost:" + port!!
    }

    @Test
    fun `balances a portfolio comparing to the ideal distribution`() {
        val jsonPayload = RobotAdvisorResponseObjectMother.valid()
        println(jsonPayload)

        val response = buyFunds(jsonPayload)

        Assertions.assertThat(response.isRight())
        response.bimap(
                {
                    Assertions.fail("expected a right")
                },
                { (response, result) ->
                    val get = result.get()
                    Assertions.assertThat(response.statusCode).isEqualTo(200)
                    when (result) {
                        is Result.Success -> {
                            println(result.value)
                        }
                        else -> {
                            Assertions.fail("expected a Result.success")
                        }
                    }
                })
    }

    private fun buyFunds(jsonPayload: String): Either<Exception, Pair<Response, Result<String, FuelError>>> {
        val httpPost = "/operations/".httpPost().body(jsonPayload, Charsets.UTF_8).header("Content-Type" to "application/json")
        try {
            val (_, response, result) = httpPost.responseString()
            return Either.right(Pair(response, result))
        } catch (e: Exception) {
            e.printStackTrace()
            return Either.left(e)
        }

    }

}

@Configuration
class FakeOperationPerformerService {
    @Autowired
    var driver: WebDriver? = null

    @Bean
    fun fundsOperationPerformerService(): FundsOperationPerformerService {
        return object : com.gmaur.investment.r4automator.app.FundsOperationPerformerService(driver!!, FundsConfiguration()) {
            override fun perform(fund: FundPurchase): Either<Exception, Path> {
                return Either.left(RuntimeException())
            }
        }
    }

}
