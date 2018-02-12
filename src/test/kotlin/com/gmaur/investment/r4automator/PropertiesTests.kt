package com.gmaur.investment.r4automator

import com.gmaur.investment.r4automator.infrastructure.funds.FundsConfiguration
import com.gmaur.investment.r4automator.infrastructure.login.LoginConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@EnableAutoConfiguration()
@ContextConfiguration(classes = [FundsConfiguration::class, LoginConfiguration::class])
@ComponentScan(basePackages = ["com.gmaur.investment.r4automator"])
class PropertiesTests {

    @Autowired
    private lateinit var fundsConfiguration: FundsConfiguration

    @Autowired
    private lateinit var loginConfiguration: LoginConfiguration

    @Test
    fun `the login and the funds have different configuration`() {
        val url = loginConfiguration.url
        val fundsurl = fundsConfiguration.fundsurl
        assertThat(url).isNotNull()
        assertThat(fundsurl).isNotNull()
        assertThat(url).isNotEqualToIgnoringCase(fundsurl)
    }

}
