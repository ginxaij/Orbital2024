package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.db.StockHoldingDao
import com.example.wealthwings.db.StockHoldingDatabase
import com.example.wealthwings.model.StockHolding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StockHoldingViewModel(application: Application) : AndroidViewModel(application) {
    private var currentUserUid: String? = null
//    private val stockHoldingDao: StockHoldingDao =
//        StockHoldingDatabase.getInstance(application).getStockHoldingDao()

    var stockHoldingList: LiveData<List<StockHolding>> = FirebaseDB().readHoldings(currentUserUid.toString())
    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(stockHoldingList) { stock ->
            value = stock.sumOf { it.totalPrice }
        }
    }

    val totalAmount: LiveData<Double> get() = _totalAmount

    fun setCurrentUser(input: String?) {
        currentUserUid = input
        // Reload expenses for the current user
        stockHoldingList = FirebaseDB().readHoldings(currentUserUid.toString())
        _totalAmount.removeSource(stockHoldingList)
        _totalAmount.addSource(stockHoldingList) { stock ->
            _totalAmount.value = stock.sumOf { it.totalPrice }
        }
    }

    fun addHolding(newStockHolding: StockHolding) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDB().writeHolding(currentUserUid.toString(), newStockHolding)
//            val existingHolding = stockHoldingDao.getHoldingBySymbol(newStockHolding.symbol)
//            if (existingHolding != null) {
//                // Update the existing holding with the new quantity and total price
//                val updatedQuantity = existingHolding.quantity + newStockHolding.quantity
//                val updatedTotalPrice = existingHolding.totalPrice + (newStockHolding.price * newStockHolding.quantity)
//                stockHoldingDao.updateHolding(newStockHolding.symbol, updatedQuantity, updatedTotalPrice)
//            } else {
//                // Set totalPrice initially to price * quantity
//                val initialTotalPrice = newStockHolding.price * newStockHolding.quantity
//                val newHolding = newStockHolding.copy(totalPrice = initialTotalPrice)
//                stockHoldingDao.addHolding(newHolding)
//            }
        }
    }

//    fun deleteHolding(id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            stockHoldingDao.deleteHolding(id)
//        }
//    }

    fun deleteAllHoldings() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDB().removeAllHoldings()
//            stockHoldingDao.deleteAllHoldings()
        }
    }
}
