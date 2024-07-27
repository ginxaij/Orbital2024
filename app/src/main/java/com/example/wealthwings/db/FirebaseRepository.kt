package com.example.wealthwings.db

import androidx.lifecycle.LiveData
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.StockHolding
import java.time.LocalDate

interface FirebaseRepository {
    fun writeNewUser(uid: String, password: String, email: String)
    fun writePassword(newPassword: String)
    fun readEmail(): String
    fun readPassword(): String
    fun writeEmail(newEmail: String)
    fun deleteUser(uid: String)
    fun loadExpenses(uid: String, startDate: LocalDate?, endDate: LocalDate?): LiveData<List<Expense>>
    fun writeExpense(uid: String, expense: Expense)
    fun removeExpense(uid: String, expenseUID: String)
    fun removeAllExpense(uid: String)
    fun readHoldings(uid: String): LiveData<List<StockHolding>>
    fun writeHolding(uid: String, stock: StockHolding)
    fun removeAllHoldings()
}