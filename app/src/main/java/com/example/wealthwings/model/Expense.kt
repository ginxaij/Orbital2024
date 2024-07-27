package com.example.wealthwings.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

@RequiresApi(Build.VERSION_CODES.O)

@Entity
data class Expense @RequiresApi(Build.VERSION_CODES.O) constructor(
    //@PrimaryKey(autoGenerate = true)
    @PrimaryKey
    var id: String = "", // Unique identifier for the expense
    val amount: Double = 0.0,
    val category: String = "",
    val date: String = "",
    val note: String? = null
)
