package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.StockHoldingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailScreen(navController: NavController, viewModel: StockHoldingViewModel, symbol: String) {
    val stockHolding by viewModel.stockHoldingList.observeAsState(emptyList())
    val stock = stockHolding.find { it.symbol == symbol }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stock?.name ?: "Stock Details") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
            )
        },
        content = { innerPadding ->
            stock?.let {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Name: ${stock.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Symbol: ${stock.symbol}", fontSize = 16.sp, color = Color.Gray)
                    Text(text = "Quantity: ${stock.quantity}", fontSize = 16.sp, color = Color.Gray)
                    Text(text = "Average Price: $${"%.2f".format(stock.totalPrice / stock.quantity)}", fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.deleteHolding(stock.symbol)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                }
            } ?: run {
                Text("Stock not found", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }
    )
}
