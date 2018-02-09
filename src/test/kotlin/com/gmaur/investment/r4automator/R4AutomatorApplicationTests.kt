package com.gmaur.investment.r4automator

import com.gmaur.investment.r4automator.app.R4AutomatorApplication
import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(R4AutomatorApplication::class))
class R4AutomatorApplicationTests {

    @Autowired
    private lateinit var fundsConfiguration: FundsConfiguration

    @Autowired
    private lateinit var loginConfiguration: LoginConfiguration

    @Test
    fun contextLoads() {
    }

    @Test
    fun different_configuration() {
        val url = loginConfiguration.url
        val fundsurl = fundsConfiguration.fundsurl
        assertThat(url).isNotEqualToIgnoringCase(fundsurl)
    }

}
