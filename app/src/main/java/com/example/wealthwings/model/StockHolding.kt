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
    var price: Double = 0.0,
    var totalPrice: Double = 0.0, // Total price paid for the stock
    var quantity: Int = 0
)
