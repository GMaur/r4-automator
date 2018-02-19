package com.gmaur.investment.r4automator.domain

data class Fund(val isin: ISIN, val amount: Amount) : Asset
data class Cash(val amount: Amount) : Asset

interface Asset
