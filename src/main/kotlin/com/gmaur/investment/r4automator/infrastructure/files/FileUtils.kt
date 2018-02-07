package com.gmaur.investment.r4automator.infrastructure.files

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path

object FileUtils {
    fun readAllLinesAsString(path: Path): String {
        return Files.readAllLines(path).joinToString("")
    }
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