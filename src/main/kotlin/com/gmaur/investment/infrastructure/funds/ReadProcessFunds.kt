package com.gmaur.investment.infrastructure.funds

import org.jsoup.Jsoup
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths


class F(val rawHtml: String) {

    fun run() {
        val doc = Jsoup.parse(rawHtml, "http://r4.com/")
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
    val rawHtml = Files.readAllLines(Paths.get("/tmp/out613564031102738840.html")).joinToString("")
    F(rawHtml).run()
}

