package com.example.wealthwings.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

@RequiresApi(Build.VERSION_CODES.O)
@Entity
data class StockHolding(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Unique identifier for the stock holding
    val symbol: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val totalPrice: Double = 0.0, // Total price paid for the stock
    val quantity: Int = 0
)
