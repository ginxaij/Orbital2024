package com.example.wealthwings.db

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.StockHolding
import com.example.wealthwings.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

// This file contains all functions that reads and writes to the database.

// This is to instantiate and get reference of database
class FirebaseDB {
    val firebaseDatabase =
        FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val usersReference = firebaseDatabase.getReference("users")

    // This function creates new user
    fun writeNewUser(uid: String, password: String, email: String) {
        val database =
            FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users")
        val user = User(email, password)
        database.child(uid).setValue(user)
    }

    fun writePassword(uid: String = FirebaseAuth.getInstance().uid.toString(), newPassword: String) {
        val expenseRef =
            usersReference.child(uid).child("password")
        expenseRef.setValue(newPassword)
    }

    fun readEmail(): String {
        var email = ""
        val expenseRef =
            usersReference.child(FirebaseAuth.getInstance().uid.toString()).child("email")
        expenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (emailDB in snapshot.children) {
                    email = emailDB.getValue(String::class.java).toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read email", error.toException())
            }
        })
        return email
    }

    fun readPassword(): String {
        var password = ""
        val expenseRef =
            usersReference.child(FirebaseAuth.getInstance().uid.toString()).child("password")
        expenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (emailDB in snapshot.children) {
                    password = emailDB.getValue(String::class.java).toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read password", error.toException())
            }
        })
        return password
    }

    fun writeEmail(uid: String = FirebaseAuth.getInstance().uid.toString(), newEmail: String) {
        val expenseRef =
            usersReference.child(uid).child("email")
        expenseRef.setValue(newEmail)
    }

    fun deleteUser(uid: String) {
        usersReference.child(uid).removeValue().addOnSuccessListener {
            // User deleted successfully
            Log.d("User Delete", "User deleted successfully.")
        }
            .addOnFailureListener { exception ->
                // Handle the failure
                Log.e("User Delete", "Error deleting User", exception)
            }
    }

    // This function loads all expenses to be displayed on expenses page
    fun loadExpenses(
        uid: String,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): LiveData<List<Expense>> {
        if (uid != null) {
            val expensesRef = usersReference.child(uid).child("expenses")
            val expensesLiveData = MutableLiveData<List<Expense>>()
            expensesRef.addValueEventListener(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    val expenses = mutableListOf<Expense>()
                    for (expenseSnapshot in snapshot.children) {
                        val expense = expenseSnapshot.getValue(Expense::class.java)
                        if (startDate == null || endDate == null) {
                            expense?.let {
                                expenses.add(it)
                            }
                        } else {
                            if (startDate!! <= LocalDate.parse(expense!!.date) &&
                                LocalDate.parse(expense.date) <= endDate
                            ) {
                                expense.let {
                                    expenses.add(it)
                                }
                            }
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

    // This function updates an existing expense in the database
    fun updateExpense(uid: String, expense: Expense) {
        val expenseRef = usersReference.child(uid).child("expenses").child(expense.id)
        expenseRef.setValue(expense)
            .addOnSuccessListener {
                // Data updated successfully
                println("Expense updated successfully!")
            }
            .addOnFailureListener {
                // Failed to update data
                println("Error updating expense: ${it.message}")
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

    // This function loads a specific expense by ID
    fun loadExpenseById(uid: String, expenseId: String): LiveData<Expense?> {
        val expenseLiveData = MutableLiveData<Expense?>()
        val expenseRef = usersReference.child(uid).child("expenses").child(expenseId)
        expenseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val expense = snapshot.getValue(Expense::class.java)
                expenseLiveData.value = expense
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read expense", error.toException())
            }
        })
        return expenseLiveData
    }

    fun readHoldings(uid: String): LiveData<List<StockHolding>> {
        if (uid != null) {
            val stockHoldingsRef = usersReference.child(uid).child("stockHoldings")
            val stockHoldingsLiveData = MutableLiveData<List<StockHolding>>()
            stockHoldingsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val stockHoldings = mutableListOf<StockHolding>()
                    for (stockHoldingSnapshot in snapshot.children) {
                        val holding = stockHoldingSnapshot.getValue(StockHolding::class.java)
                        holding?.let {
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
        val cleanedSymbol = stock.symbol.replace(".", "")
        val stockHoldingRef = usersReference.child(uid).child("stockHoldings").child(cleanedSymbol)
        var updatedStock = stock
        stockHoldingRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                if (dataSnapshot.exists()) {
                    updatedStock.totalPrice += dataSnapshot.getValue(StockHolding::class.java)!!.totalPrice
                    updatedStock.quantity += dataSnapshot.getValue(StockHolding::class.java)!!.quantity
                }
            }
            stockHoldingRef.setValue(updatedStock)
        }
    }

    fun removeAllHoldings() {
        val stockHoldingRef = usersReference.child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("stockHoldings")
        stockHoldingRef.removeValue()
    }
}
