package com.example.wealthwings.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    // To track cumulative amount
    val totalAmount: StateFlow<Double> = _expenses.map { expenses ->
        expenses.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            val updatedList = _expenses.value.toMutableList().apply {
                add(expense)
            }
            _expenses.value = updatedList
        }
    }

    // Example function to populate data on app start or for testing
    init {
        populateTestData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateTestData() {
        addExpense(Expense(id = "1", amount = 50.0, category = "Food", date = LocalDate.now()))
        addExpense(Expense(id = "2", amount = 150.0, category = "Utilities", date = LocalDate.now()))
    }
}
