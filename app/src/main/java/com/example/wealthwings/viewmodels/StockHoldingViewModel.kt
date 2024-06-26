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
    private val stockHoldingDao: StockHoldingDao = StockHoldingDatabase.getInstance(application).getStockHoldingDao()

    val stockHolding: LiveData<List<StockHolding>> = stockHoldingDao.getAllHoldings()
    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(stockHolding) { stockHoldingList ->
            value = stockHoldingList.sumOf { it.price * it.quantity }
        }
    }
    val totalAmount: LiveData<Double> get() = _totalAmount

    fun addHolding(stockHolding: StockHolding) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingHolding = stockHoldingDao.getHoldingByName(stockHolding.name)
            if (existingHolding != null) {
                stockHoldingDao.updateQuantity(stockHolding.name, stockHolding.quantity)
            } else {
                stockHoldingDao.addHolding(stockHolding)
            }
        }
    }

//    fun addHolding(stockHolding: StockHolding) {
//        viewModelScope.launch(Dispatchers.IO) {
//            stockHoldingDao.addHolding(stockHolding)
//        }
//    }

    fun deleteHolding(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stockHoldingDao.deleteHolding(id)
        }
    }
}