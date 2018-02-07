package com.gmaur.investment.infrastructure.funds

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.math.BigDecimal


class ParseFunds(val rawHtml: String) {

    fun run(): List<AssetDTO> {
        val doc = Jsoup.parse(rawHtml, "http://r4.com/")
        val assetsTable = doc.select(".tablemorning.table > tbody")[0]
        fun isin(element: Element) = element.attr("data-isin")

        var elements = assetsTable.children()
                .filter { element -> isin(element) != "" }
                .map { element ->
                    val isin = ISIN(isin(element))
                    var valueElement = element.getElementsByTag("td").flatMap { it -> it.getElementsByClass("fndVal_CNT_PATRIMONIO") }.first()

                    val value = valueElement.attr("data-fndval")
                    AssetDTO(isin, BigDecimal(value))
                }

        return elements
    }


    data class ISIN(val value: String)


    data class AssetDTO(val isin: ISIN, val amount: BigDecimal)
}

