package com.gmaur.investment.r4automator.app

import com.gmaur.investment.r4automator.infrastructure.files.FileUtils
import com.gmaur.investment.r4automator.infrastructure.funds.ParseFunds
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.nio.file.Paths

class R4FundsParser {

    private val filePickerProvider: FilePickerProvider

    constructor() {
        this.filePickerProvider = FilePickerProvider.aNew()
    }

    fun run(args: Array<String>) {

        val file = filePickerProvider.request()
        val funds = ParseFunds(FileUtils.readAllLinesAsString(Paths.get(file))).run()
        for (fund in funds) {
            println(fund)
        }
    }
}


fun main(args: Array<String>) {
    R4FundsParser().run(args)
}

class FilePickerProvider(private val input: BufferedReader, private val out: PrintStream) {
    fun request(): String {
        out.print("Input the absolute path to the file:")
        return input.readLine()
    }

    companion object Factory {
        fun aNew(): FilePickerProvider = FilePickerProvider(BufferedReader(InputStreamReader(System.`in`)), System.out)
    }
}