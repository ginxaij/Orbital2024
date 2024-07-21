package com.example.wealthwings.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.CompanyOverviewResponse
import com.example.wealthwings.viewmodels.CompanyFinancialsViewModel

@Composable
fun CompanyDetailsScreen(navController: NavController, viewModel: CompanyFinancialsViewModel, symbol: String) {
    val companyOverview: CompanyOverviewResponse? by viewModel.companyOverview.collectAsState()
    val latestPrice: String? by viewModel.latestPrice.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = symbol) {
        viewModel.fetchCompanyOverview(symbol)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        companyOverview?.let { overview ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = overview.name ?: "N/A", style = MaterialTheme.typography.bodyMedium)
                Text(text = overview.description ?: "N/A")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Sector: ${overview.sector ?: "N/A"}")
                Text(text = "Industry: ${overview.industry ?: "N/A"}")
                Text(text = "Market Cap: ${overview.marketCapitalization ?: "N/A"}")
                Text(text = "PE Ratio: ${overview.peRatio ?: "N/A"}")
                Text(text = "Latest Price: ${latestPrice ?: "N/A"}")
                // Add more financial details as needed
            }
        } ?: run {
            Text("No data available")
        }
    }
}
//@Composable
//fun CompanyDetailsScreen(navController: NavController, viewModel: CompanyFinancialsViewModel, symbol: String) {
//    val companyOverview: CompanyOverviewResponse? by viewModel.companyOverview.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//
//    LaunchedEffect(key1 = symbol) {
//        viewModel.fetchCompanyOverview(symbol)
//    }
//
//    if (isLoading) {
//        CircularProgressIndicator()
//    } else {
//        companyOverview?.let { overview ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                Text(text = overview.name ?: "N/A", style = MaterialTheme.typography.bodyMedium)
//                Text(text = overview.description ?: "N/A")
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "Sector: ${overview.sector ?: "N/A"}")
//                Text(text = "Industry: ${overview.industry ?: "N/A"}")
//                Text(text = "Market Cap: ${overview.marketCapitalization ?: "N/A"}")
//                Text(text = "PE Ratio: ${overview.peRatio ?: "N/A"}")
//                // Add more financial details as needed
//            }
//        } ?: run {
//            Text("No data available")
//        }
//    }
//}