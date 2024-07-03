package com.example.wealthwings.db

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.StockHolding
import com.example.wealthwings.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// This file contains all functions that reads and writes to the database.

// This is to instantiate and get reference of database
val firebaseDatabase = FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
val usersReference = firebaseDatabase.getReference("users")

// This function creates new user
fun writeNewUser(uid: String, password: String, email: String) {
    val database = FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("users")
    val user = User(email, password)
    Log.i("Info", "Attempt to write")
    database.child(uid).setValue(user)
    Log.i("Info", "Attempt to write finish")
}

// This function loads all expenses to be displayed on expenses page
fun loadExpenses(uid : String): LiveData<List<Expense>> {
    if (uid != null) {
        val expensesRef = usersReference.child(uid).child("expenses")
        val expensesLiveData = MutableLiveData<List<Expense>>()
        expensesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val expenses = mutableListOf<Expense>()
                for (expenseSnapshot in snapshot.children) {
                    val expense = expenseSnapshot.getValue(Expense::class.java)
                    expense?.let {
                        expenses.add(it)
                    }
                }
                expensesLiveData.value = expenses
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read expenses", error.toException())
            }
        })
        return expensesLiveData
    } else {
        return MutableLiveData<List<Expense>>().apply { value = emptyList() }
    }
}

// This function writes a new expense to the database
fun writeExpense(uid: String, expense: Expense) {
    val userExpensesRef = usersReference.child(uid).child("expenses")
    val newExpenseRef = userExpensesRef.push() // Generate Unique Key
    expense.id = newExpenseRef.key.toString()
    newExpenseRef.setValue(expense)
        .addOnSuccessListener {
            // Data updated successfully
            println("Expenses updated successfully!")
        }
        .addOnFailureListener {
            // Failed to update data
            println("Error updating expenses: ${it.message}")
        }
}

// This function removes specified expense from the database
fun removeExpense(uid: String, expenseUID: String) {
        val expenseRef = usersReference.child(uid).child("expenses").child(expenseUID)
        expenseRef.removeValue()
            .addOnSuccessListener {
                // Expense deleted successfully
                Log.d("ExpenseViewModel", "Expense deleted successfully.")
            }
            .addOnFailureListener { exception ->
                // Handle the failure
                Log.e("ExpenseViewModel", "Error deleting expense", exception)
            }
}

// This function removes all expenses from the database
fun removeAllExpense(uid: String) {
    val expenseRef = usersReference.child(uid).child("expenses")
    expenseRef.removeValue()
        .addOnSuccessListener {
            // Expense deleted successfully
            Log.d("ExpenseViewModel", "All expenses deleted successfully.")
        }
        .addOnFailureListener { exception ->
            // Handle the failure
            Log.e("ExpenseViewModel", "Error deleting all expenses", exception)
        }
}

fun readHoldings(uid : String): LiveData<List<StockHolding>> {
    if (uid != null) {
        val stockHoldingsRef = usersReference.child(uid).child("stockHoldings")
        val stockHoldingsLiveData = MutableLiveData<List<StockHolding>>()
        stockHoldingsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stockHoldings = mutableListOf<StockHolding>()
                for (stockHoldingSnapshot in snapshot.children) {
                    val expense = stockHoldingSnapshot.getValue(StockHolding::class.java)
                    expense?.let {
                        stockHoldings.add(it)
                    }
                }
                stockHoldingsLiveData.value = stockHoldings
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read stockHoldings", error.toException())
            }
        })
        return stockHoldingsLiveData
    } else {
        return MutableLiveData<List<StockHolding>>().apply { value = emptyList() }
    }
}

fun writeHolding(uid: String, stock: StockHolding) {
    val stockHoldingRef = usersReference.child(uid).child("stockHoldings").child(stock.name)
    var updatedQuantity = 0
    var updatedAmount = 0.0

    val listOfHoldings = readHoldings(uid).value
    if (listOfHoldings != null) {
        for (holding in listOfHoldings) {
            if (holding.name == stock.name) {
                updatedQuantity = holding.quantity + holding.quantity
                updatedAmount = holding.price
            }
        }
    }

}
