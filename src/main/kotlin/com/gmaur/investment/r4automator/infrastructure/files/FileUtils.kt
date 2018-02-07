package com.gmaur.investment.r4automator.infrastructure.files

import java.nio.file.Files
import java.nio.file.Path

object FileUtils {
    fun readAllLinesAsString(path: Path): String {
        return Files.readAllLines(path).joinToString("")
    }
}