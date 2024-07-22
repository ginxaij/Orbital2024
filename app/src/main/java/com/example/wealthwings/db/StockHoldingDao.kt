package com.example.wealthwings.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wealthwings.model.StockHolding

//@Dao
//interface StockHoldingDao {
//    @Query("SELECT * FROM StockHolding")
//    fun getAllHoldings(): LiveData<List<StockHolding>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addHolding(stockHolding: StockHolding)
//
//    @Query("DELETE FROM stockholding WHERE id = :id")
//    fun deleteHolding(id: Int)
//
//    @Query("SELECT * FROM stockholding WHERE name = :name LIMIT 1")
//    fun getHoldingByName(name: String): StockHolding?
//
//    @Query("UPDATE stockholding SET quantity = quantity + :quantity WHERE name = :name")
//    fun updateQuantity(name: String, quantity: Int)
//
//    @Query("DELETE FROM stockholding")
//    fun deleteAllHoldings()
//}


@Dao
interface StockHoldingDao {
    @Query("SELECT * FROM StockHolding")
    fun getAllHoldings(): LiveData<List<StockHolding>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHolding(stockHolding: StockHolding)

    @Query("DELETE FROM stockholding WHERE id = :id")
    fun deleteHolding(id: Int)

    @Query("SELECT * FROM stockholding WHERE symbol = :symbol LIMIT 1")
    fun getHoldingBySymbol(symbol: String): StockHolding?

    @Query("UPDATE stockholding SET quantity = :quantity, totalPrice = :totalPrice WHERE symbol = :symbol")
    fun updateHolding(symbol: String, quantity: Int, totalPrice: Double)

    @Query("DELETE FROM stockholding")
    fun deleteAllHoldings()
}

