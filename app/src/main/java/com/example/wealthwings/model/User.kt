package com.example.wealthwings.model

data class User (
    var email: String? = null,
    var password: String? = null,
    var expenses: List<Expense>? = null,
    var stockHoldings: List<StockHolding>? = null
)