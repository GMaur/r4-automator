package com.gmaur.investment.r4automator.app

import java.io.BufferedReader
import java.io.InputStreamReader

object UserInteraction {
    private val bufferedReader = BufferedReader(InputStreamReader(System.`in`))

    fun `confirm?`(message: String): Boolean {
        println(message + " yes/no")
        return bufferedReader.readLine().equals("yes", true)
    }
}