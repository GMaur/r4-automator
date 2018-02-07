package com.gmaur.investment.infrastructure.funds

import org.jsoup.Jsoup
import java.io.File
import java.math.BigDecimal


class F {

    fun run() {
        val input = File("/tmp/out613564031102738840.html")
        val doc = Jsoup.parse(input, "UTF-8", "http://r4.com/")
        val assetsTable = doc.select(".tablemorning.table > tbody")[0]

        var elements = assetsTable.children()
                .filter { element -> element.attr("data-isin") != "" }
                .map { element ->
                    val isin = ISIN(element.attr("data-isin"))
                    var valueElement = element.getElementsByTag("td").flatMap { it -> it.getElementsByClass("fndVal_CNT_PATRIMONIO") }.first()

                    val value = valueElement.attr("data-fndval")
                    AssetDTO(isin, BigDecimal(value))
                }

        for (element in elements) {
            println(element)
        }
    }

    data class ISIN(val value: String)


    data class AssetDTO(val isin: ISIN, val amount: BigDecimal)
}

fun main(args: Array<String>) {
    F().run()
}

