package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.StockHoldingViewModel
import com.example.wealthwings.viewmodels.StockSearchViewModel

//dostogadre@gufum.com
//tavl65xr57@smykwb.com
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Investment(navController: NavController, viewModel: StockHoldingViewModel, stockSearchViewModel: StockSearchViewModel) {
    val stockHolding by viewModel.stockHoldingList.observeAsState(emptyList())
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
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
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
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Your Holdings", fontSize = 30.sp)
                            Text("${totalAmount}", fontSize = 30.sp)

                            var sortedList = stockHolding.sortedByDescending { it.totalPrice }
                            var top4 = sortedList.take(4)
                            var remainder = sortedList.drop(4)
                            var remainderTotal = remainder.sumOf { it.totalPrice }

                            val pieChartData = top4.associate { it.symbol to it.totalPrice }
                                .plus("Rest Of Portfolio" to remainderTotal)

                            PieChart(
                                data = pieChartData
                            )
                        }
                    }
                    items(stockHolding, key = { it.symbol }) { holding ->
                        val averagePrice =
                            if (holding.quantity > 0) holding.totalPrice / holding.quantity else 0.0

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
                                Column(
                                    Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(0.7f)
                                ) {
                                    Text(text = "Name: ${holding.name}", fontSize = 16.sp)
                                    Text(text = "Symbol: ${holding.symbol}", fontSize = 16.sp)
                                }
                                Column(
                                    Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(text = "Amount: ${holding.quantity}", fontSize = 16.sp)
                                    Text(
                                        text = "Avg Price: ${"%.2f".format(averagePrice)}",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        })
}