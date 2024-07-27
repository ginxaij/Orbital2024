package com.example.wealthwings.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wealthwings.StockMatch

@Composable
fun StockList(
    modifier: Modifier = Modifier,
    searchResults: List<StockMatch>,
    isLoading: Boolean,
    onItemClick: (StockMatch) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(searchResults) { company ->
                    CompanyItem(company = company, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onItemClick(company) })
                }
            }
        }
    }
}

@Composable
fun CompanyItem(company: StockMatch, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = company.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = company.symbol, fontSize = 14.sp, color = Color.Gray)
        }
    }
}
