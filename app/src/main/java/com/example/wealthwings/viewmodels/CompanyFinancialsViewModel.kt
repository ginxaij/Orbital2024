package com.example.wealthwings.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.CompanyOverviewResponse
import com.example.wealthwings.StockApi
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
                val response = api.getCompanyOverview(symbol)
                _companyOverview.value = response
                fetchLatestPrice(symbol)
            } catch (e: Exception) {
                _companyOverview.value = null
                Log.e("CompanyFinancialsVM", "Error fetching company overview", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchLatestPrice(symbol: String) {
        try {
            val response = api.getIntradayPrices(symbol)
            val latestEntry = response.timeSeries.values.firstOrNull()
            _latestPrice.value = latestEntry?.close
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