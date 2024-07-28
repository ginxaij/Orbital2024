package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.StockHolding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StockHoldingViewModel(application: Application) : AndroidViewModel(application) {
    private var currentUserUid: String? = null

    var stockHoldingList: LiveData<List<StockHolding>> = MutableLiveData()
    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(stockHoldingList) { stock ->
            value = stock.sumOf { it.totalPrice }
        }
    }

    val totalAmount: LiveData<Double> get() = _totalAmount

    init {
        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        currentUserUid?.let { uid ->
            stockHoldingList = FirebaseDB().readHoldings(uid)
        }
    }

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
            currentUserUid?.let { uid ->
                FirebaseDB().writeHolding(uid, newStockHolding)
            }
        }
    }

    fun deleteHolding(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentUserUid?.let { uid ->
                FirebaseDB().removeHolding(uid, symbol)
            }
        }
    }

    fun deleteAllHoldings() {
        viewModelScope.launch(Dispatchers.IO) {
            currentUserUid?.let { uid ->
                FirebaseDB().removeAllHoldings()
            }
        }
    }
}
