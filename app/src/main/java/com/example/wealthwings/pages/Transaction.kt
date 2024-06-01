package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.ExpenseViewModel

//import com.example.wealthwings.ui.theme.TopAppBarBackGround


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transaction(navController: NavController, viewModel: ExpenseViewModel) {

    val expenses = viewModel.expenses.collectAsState()
    val totalAmount = viewModel.totalAmount.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },

        floatingActionButton = {
                   LargeButton(navController)
        }

        ,
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(text = "Total Expenses: ${totalAmount.value}")
                LazyColumn {
                    items(expenses.value, key = { it.id }) { expense ->
                        Text("Amount: ${expense.amount} - Category: ${expense.category} - Date: ${expense.date}")
                    }
                }
            }
        })


}