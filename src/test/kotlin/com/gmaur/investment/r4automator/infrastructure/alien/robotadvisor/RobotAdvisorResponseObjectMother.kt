package com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor

object RobotAdvisorResponseObjectMother {
    fun valid(): String {
        val operationsJSON = """
{
  "operations" : [ {
    "type" : "purchase",
    "asset" : {
      "isin" : "LU0",
      "type" : "fund"
    },
    "amount" : {
      "value" : "0.00",
      "currency" : "EUR"
    }
  }, {
    "type" : "purchase",
    "asset" : {
      "isin" : "LU1",
      "type" : "fund"
    },
    "amount" : {
      "value" : "1.00",
      "currency" : "EUR"
    }
  } ]
}
"""
        return operationsJSON
    }
}