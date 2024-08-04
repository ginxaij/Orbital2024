package com.example.wealthwings.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.CompanyOverviewResponse
import com.example.wealthwings.StockApi
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyFinancialsViewModel @Inject constructor(
    private val api: StockApi
) : ViewModel() {

    private val _companyOverview = MutableStateFlow<CompanyOverviewResponse?>(null)
    val companyOverview: StateFlow<CompanyOverviewResponse?> get() = _companyOverview

    private val _latestPrice = MutableStateFlow<String?>(null)
    val latestPrice: StateFlow<String?> get() = _latestPrice

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchCompanyOverview(symbol: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.i("Info", "start fetch")
                val response = api.getCompanyOverview(symbol = symbol)
                Log.i("response", response.toString())
                _companyOverview.value = response
                fetchLatestPrice(symbol)
            } catch (e: Exception) {
                _companyOverview.value = null
                Log.e("CompanyFinancialsVM", "Error fetching company overview", e)
            } finally {
                _isLoading.value = false
                Log.e("CompanyFinancialsVM", "Finish Fetching Company Overview")
            }
        }
    }

    private suspend fun fetchLatestPrice(symbol: String) {
        try {
            val response = api.getIntradayPrices(symbol)
            val timeSeries = response.timeSeries
            if (timeSeries != null) {
                val latestEntry = timeSeries.values.firstOrNull()
                _latestPrice.value = latestEntry?.close
            } else {
                _latestPrice.value = null
            }
        } catch (e: Exception) {
            _latestPrice.value = null
            Log.e("CompanyFinancialsVM", "Error fetching latest price", e)
        }
    }
}
//@HiltViewModel
//class CompanyFinancialsViewModel @Inject constructor(
//    private val api: StockApi
//) : ViewModel() {
//
//    private val _companyOverview = MutableStateFlow<CompanyOverviewResponse?>(null)
//    val companyOverview: StateFlow<CompanyOverviewResponse?> get() = _companyOverview
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> get() = _isLoading
//
//    fun fetchCompanyOverview(symbol: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val response = api.getCompanyOverview(symbol)
//                _companyOverview.value = response
//            } catch (e: Exception) {
//                _companyOverview.value = null
//                Log.e("CompanyFinancialsVM", "Error fetching company overview", e)
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}