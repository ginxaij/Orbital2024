package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.StockHoldingViewModel
import com.example.wealthwings.viewmodels.StockSearchViewModel

//import com.example.wealthwings.ui.theme.TopAppBarBackGround

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Investment(navController: NavController, viewModel: StockHoldingViewModel, stockSearchViewModel: StockSearchViewModel) {
    val stockHolding by viewModel.stockHolding.observeAsState(emptyList())
    val totalAmount by viewModel.totalAmount.observeAsState(0.0)
    var query by remember { mutableStateOf("") }
    val searchResults by stockSearchViewModel.searchResults.collectAsState()
    val isLoading by stockSearchViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Investments") },
                actions = {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            if (query.isNotBlank()) {
                                stockSearchViewModel.searchStocks(query)
                            }
                        },
                        placeholder = { Text("Search Stocks") },
                        trailingIcon = {
                            IconButton(onClick = { stockSearchViewModel.searchStocks(query) }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
            )
        },
        floatingActionButton = {
            LargeButton(navController, "investment/addstockholding")
        },
        content = { innerPadding ->
            if (query.isNotBlank() && !searchResults.isNullOrEmpty()) {
                StockList(
                    modifier = Modifier.padding(innerPadding),
                    searchResults = searchResults,
                    isLoading = isLoading,
                    onItemClick = { symbol ->
                        navController.navigate("companyDetails/$symbol")
                    }
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    Text(text = "Your Holdings", fontSize = 30.sp)
                    Text("${totalAmount}", fontSize = 30.sp)
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(stockHolding, key = { it.id }) { stockHolding ->
                            Column(
                                Modifier
                                    .padding()
                                    .clip(Shapes.medium)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding()
                                        .background(BackgroundElevated),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Name: ${stockHolding.name}", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.padding(12.dp))
                                    Text(text = "Amount: ${stockHolding.quantity}", fontSize = 16.sp)
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    )
}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Investment(navController: NavController, viewModel: StockHoldingViewModel, stockSearchViewModel: StockSearchViewModel) {
//    val stockHolding by viewModel.stockHolding.observeAsState(emptyList())
//    val totalAmount by viewModel.totalAmount.observeAsState(0.0)
//    var query by remember { mutableStateOf("") }
//    val searchResults by stockSearchViewModel.searchResults.collectAsState()
//    val isLoading by stockSearchViewModel.isLoading.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Investments") },
//                actions = {
//                    TextField(
//                        value = query,
//                        onValueChange = {
//                            query = it
//                            if (query.isNotBlank()) {
//                                stockSearchViewModel.searchStocks(query)
//                            }
//                        },
//                        placeholder = { Text("Search Stocks") },
//                        trailingIcon = {
//                            IconButton(onClick = { stockSearchViewModel.searchStocks(query) }) {
//                                Icon(Icons.Default.Search, contentDescription = "Search")
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    )
//                },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
//            )
//        },
//        floatingActionButton = {
//            LargeButton(navController, "investment/addstockholding")
//        },
//        content = { innerPadding ->
//            if (query.isNotBlank() && !searchResults.isNullOrEmpty()) {
//                StockList(
//                    modifier = Modifier.padding(innerPadding),
//                    searchResults = searchResults,
//                    isLoading = isLoading
//                )
//            } else {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .padding(innerPadding)
//                        .fillMaxWidth()
//                ) {
//                    Text(text = "Your Holdings", fontSize = 30.sp)
//                    Text("${totalAmount}", fontSize = 30.sp)
//                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                        items(stockHolding, key = { it.id }) { stockHolding ->
//                            Column(
//                                Modifier
//                                    .padding()
//                                    .clip(Shapes.medium)
//                            ) {
//                                Row(
//                                    Modifier
//                                        .fillMaxWidth()
//                                        .padding()
//                                        .background(BackgroundElevated),
//                                    horizontalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    Text(text = "Name: ${stockHolding.name}", fontSize = 16.sp)
//                                    Spacer(modifier = Modifier.padding(12.dp))
//                                    Text(text = "Amount: ${stockHolding.quantity}", fontSize = 16.sp)
//                                }
//                                Divider()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Investment(navController: NavController, viewModel: StockHoldingViewModel) {//, searchViewModel: StockSearchViewModel) {//, searchViewModel: StockSearchViewModel) {
//    Log.d("InvestmentScreen", "Investment Composable Entered")
//    val stockHolding by viewModel.stockHolding.observeAsState(emptyList())
//    val totalAmount by viewModel.totalAmount.observeAsState(0.0)
////    val searchResults by searchViewModel.searchResults.observeAsState(emptyList())
////    var query by remember { mutableStateOf("") }
////    val expenses = viewModel.expenses.collectAsState()
////    val totalAmount = viewModel.totalAmount.collectAsState()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Investments") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = Background
//                )
//            )
//        },
////        topBar = {
////            TopAppBar(
////                title = { Text("Investments") },
////                actions = {
////                    TextField(
////                        value = query,
////                        onValueChange = {
////                            query = it
////                            if (query.isNotBlank()) {
////                                searchViewModel.searchStocks(query)
////                            }
////                        },
////                        placeholder = { Text("Search Stocks") }
////                    )
////                },
////                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
////            )
////        },
//        floatingActionButton = {
//            LargeButton(navController, "investment/addstockholding")
//        },
//        content = { innerPadding ->
//            Column(horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxWidth()) {
//
//                Text(text = "Your Holdings", fontSize = 30.sp)
//                Text("${totalAmount}", fontSize = 30.sp) //.value
//                Column(horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier.padding(16.dp)) {
//                }
//                Spacer(modifier = Modifier.padding(24.dp))
//                LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                    items(stockHolding, key = { it.id }) { stockHolding -> //expenses.value
//                        Column(
//                            Modifier
//                                .padding()
//                                .clip(Shapes.medium)) {
//                            Row(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .padding()
//                                    .background(
//                                        BackgroundElevated
//                                    ),
//                                horizontalArrangement = Arrangement.SpaceBetween) {
//                                Text(text = "Name: ${stockHolding.name}", fontSize = 16.sp)
//                                Spacer(modifier = Modifier.padding(12.dp))
//                                Text(text = "Amount: ${stockHolding.quantity}", fontSize = 16.sp)
//                            }
////                            Row(
////                                Modifier
////                                    .fillMaxWidth()
////                                    .padding()
////                                    .background(
////                                        BackgroundElevated
////                                    ),
////                                horizontalArrangement = Arrangement.SpaceBetween) {
////                                Text(text = "Category: ${stockHolding.category}", fontSize = 12.sp)
////                                Spacer(modifier = Modifier.padding(12.dp))
////                                Text(text = "Date: ${stockHolding.date}", fontSize = 12.sp)
////                            }
//                            Divider()
//                        }
////                        Text("Amount: ${expense.amount} - Category: ${expense.category} - Date: ${expense.date}")
//                    }
//                }
//            }
//        })
//}