package com.example.wealthwings.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@RequiresApi(Build.VERSION_CODES.O)

@Entity
data class Expense @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Unique identifier for the expense
    @ColumnInfo val amount: Double = 0.0,
    @ColumnInfo val category: String = "",
    @ColumnInfo val date: String = "",
    @ColumnInfo val note: String? = null
)

