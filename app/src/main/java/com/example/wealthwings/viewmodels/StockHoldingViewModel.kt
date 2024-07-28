package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.StockHolding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StockHoldingViewModel(application: Application) : AndroidViewModel(application) {
    private var currentUserUid: String? = null

    var stockHoldingList: LiveData<List<StockHolding>> = FirebaseDB().readHoldings(currentUserUid.toString())
    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(stockHoldingList) { stock ->
            value = stock.sumOf { it.totalPrice }
        }
    }

    val totalAmount: LiveData<Double> get() = _totalAmount

    fun setCurrentUser(input: String?) {
        currentUserUid = input
        // Reload stock holdings for the current user
        stockHoldingList = FirebaseDB().readHoldings(currentUserUid.toString())
        _totalAmount.removeSource(stockHoldingList)
        _totalAmount.addSource(stockHoldingList) { stock ->
            _totalAmount.value = stock.sumOf { it.totalPrice }
        }
    }

    fun addHolding(newStockHolding: StockHolding) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDB().writeHolding(currentUserUid.toString(), newStockHolding)
        }
    }

    fun deleteHolding(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDB().removeHolding(currentUserUid.toString(), symbol)
        }
    }

    fun deleteAllHoldings() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseDB().removeAllHoldings()
        }
    }
}
