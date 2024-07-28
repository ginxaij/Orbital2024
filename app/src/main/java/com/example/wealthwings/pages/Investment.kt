package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.StockHoldingViewModel
import com.example.wealthwings.viewmodels.StockSearchViewModel

//dostogadre@gufum.com
    //tavl65xr57@smykwb.com
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
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

                            val sortedList = stockHolding.sortedByDescending { it.totalPrice }
                            val top4 = sortedList.take(4)
                            val remainder = sortedList.drop(4)
                            val remainderTotal = remainder.sumOf { it.totalPrice }

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

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("stockDetails/${holding.symbol}")
                                },
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Column(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        Modifier.fillMaxWidth(0.6f)
                                    ) {
                                        Text(text = holding.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                        Text(text = holding.symbol, fontSize = 16.sp, color = Color.Gray)
                                    }
                                    Column(
                                        Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(text = "$${"%.2f".format(averagePrice)}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                        Text(text = "Quantity: ${holding.quantity}", fontSize = 16.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


