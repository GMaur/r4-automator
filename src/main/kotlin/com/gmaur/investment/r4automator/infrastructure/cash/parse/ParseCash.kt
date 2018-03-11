package com.gmaur.investment.r4automator.infrastructure.cash.parse

import com.gmaur.investment.r4automator.domain.Amount
import com.gmaur.investment.r4automator.domain.Cash
import com.gmaur.investment.r4automator.domain.Portfolio
import org.jsoup.Jsoup
import java.math.BigDecimal

class ParseCash(val rawHtml: String) {
    fun run(): Portfolio {
        val amount = parse(rawHtml)
        return Portfolio(listOf(Cash(amount)))
    }

    private fun parse(content: String): Amount {
        val doc = Jsoup.parse(content, "http://r4.com/")
        val rawValue = doc.select("tbody[id='Saldo en EUR'] td.formato-nivel-1")
                .map { it.text() }
                .filter { it != "" }
                .first()
                .replace(".", "")
                .replace("€", "")
                .replace(",", ".")
                .trim()
        return Amount(BigDecimal(rawValue))
    }

}