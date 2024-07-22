package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.StockHoldingDao
import com.example.wealthwings.db.StockHoldingDatabase
import com.example.wealthwings.model.StockHolding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StockHoldingViewModel(application: Application) : AndroidViewModel(application) {
    private val stockHoldingDao: StockHoldingDao =
        StockHoldingDatabase.getInstance(application).getStockHoldingDao()

    var stockHolding: LiveData<List<StockHolding>> = stockHoldingDao.getAllHoldings()
    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(stockHolding) { stockHoldingList ->
            value = stockHoldingList.sumOf { it.totalPrice }
        }
    }

    val totalAmount: LiveData<Double> get() = _totalAmount

    fun addHolding(newStockHolding: StockHolding) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingHolding = stockHoldingDao.getHoldingBySymbol(newStockHolding.symbol)
            if (existingHolding != null) {
                // Update the existing holding with the new quantity and total price
                val updatedQuantity = existingHolding.quantity + newStockHolding.quantity
                val updatedTotalPrice = existingHolding.totalPrice + (newStockHolding.price * newStockHolding.quantity)
                stockHoldingDao.updateHolding(newStockHolding.symbol, updatedQuantity, updatedTotalPrice)
            } else {
                // Set totalPrice initially to price * quantity
                val initialTotalPrice = newStockHolding.price * newStockHolding.quantity
                val newHolding = newStockHolding.copy(totalPrice = initialTotalPrice)
                stockHoldingDao.addHolding(newHolding)
            }
        }
    }

    fun deleteHolding(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stockHoldingDao.deleteHolding(id)
        }
    }

    fun deleteAllHoldings() {
        viewModelScope.launch(Dispatchers.IO) {
            stockHoldingDao.deleteAllHoldings()
        }
    }
}
