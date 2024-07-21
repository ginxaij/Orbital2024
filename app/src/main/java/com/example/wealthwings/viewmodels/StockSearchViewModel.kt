package com.example.wealthwings.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.StockApi
import com.example.wealthwings.StockMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockSearchViewModel @Inject constructor(
    private val api: StockApi
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<StockMatch>>(emptyList())
    val searchResults: StateFlow<List<StockMatch>> get() = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun searchStocks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.searchStocks(query)
                if (response.matches != null) {
                    _searchResults.value = response.matches
                    Log.d("StockSearchViewModel", "Success: ${response.matches.size} items fetched")
                } else {
                    _searchResults.value = emptyList()
                    Log.d("StockSearchViewModel", "No matches found")
                }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
                Log.e("StockSearchViewModel", "Error fetching stocks", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}