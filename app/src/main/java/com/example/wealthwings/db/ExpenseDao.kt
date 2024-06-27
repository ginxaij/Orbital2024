package com.example.wealthwings.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wealthwings.model.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addExpense(expense: Expense)

    @Query("DELETE FROM expense WHERE id = :id")
    fun deleteExpense(id: Int)

    @Query("DELETE FROM expense")
    fun deleteAllExpenses()
}
