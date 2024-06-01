package com.example.wealthwings.model

import java.time.LocalDate

data class Expense(
    val id: String, // Unique identifier for the expense
    val amount: Double,
    val category: String,
    val date: LocalDate,
    val note: String? = null
)