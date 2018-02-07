package com.gmaur.investment.r4automator.infrastructure.funds

import com.gmaur.investment.r4automator.domain.Asset
import com.gmaur.investment.r4automator.domain.ISIN
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.math.BigDecimal


class ParseFunds(val rawHtml: String) {

    fun run(): List<Asset> {
        val assetsTable = obtainAssetTable(rawHtml)

        var elements = parseElements(assetsTable)

        return elements
    }

    private fun parseElements(assetsTable: Element): List<Asset> {
        fun parseElement(): (Element) -> Asset {
            return { element ->
                val isin = ISIN(isin(element))
                var valueElement = element.getElementsByTag("td").flatMap { it -> it.getElementsByClass("fndVal_CNT_PATRIMONIO") }.first()

                val value = valueElement.attr("data-fndval")
                Asset(isin, BigDecimal(value))
            }
        }

        var elements = assetsTable.children()
                .filter { element -> isin(element) != "" }
                .map(parseElement())
        return elements
    }


    private fun obtainAssetTable(rawHtml: String): Element {
        val doc = Jsoup.parse(rawHtml, "http://r4.com/")
        return doc.select(".tablemorning.table > tbody")[0]
    }

    private fun isin(element: Element) = element.attr("data-isin")

}

