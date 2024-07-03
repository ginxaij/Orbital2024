package com.example.wealthwings.pages

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
//import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.User
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.ExpenseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transaction(navController: NavController, viewModel: ExpenseViewModel) {
    val expenses by viewModel.expensesLiveData.observeAsState(emptyList())
    val totalAmount by viewModel.totalAmount.observeAsState(0.0)
    var showDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },

        floatingActionButton = {
            LargeButton(navController, "transaction/add")
        },
        content = { innerPadding ->
            Spacer(modifier = Modifier.padding(10.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                    ) {

                        Text(text = "Expenses", fontSize = 30.sp)
                        Text(
                            "$${totalAmount.toBigDecimal().setScale(2)}",
                            fontSize = 30.sp
                        ) //.value
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(13.dp)
                        ) {

                            val categories = listOf(
                                "Food & Dining", "Transportation", "Utilities",
                                "Groceries", "Miscellaneous"
                            ).sortedDescending()

                            val mappedCategories: MutableMap<String, Double> = mutableMapOf()

                            for (expense in expenses) {
                                if (mappedCategories.containsKey(expense.category)) {
                                    mappedCategories[expense.category] = mappedCategories[expense.category]!!.plus(expense.amount)
                                } else {
                                    mappedCategories[expense.category] = expense.amount
                                }
                            }

                            PieChart(
                                data = mapOf(
                                    Pair(categories[0], mappedCategories[categories[0]] ?: 0.0), // to edit according to DB
                                    Pair(categories[1], mappedCategories[categories[1]] ?: 0.0),
                                    Pair(categories[2], mappedCategories[categories[2]] ?: 0.0),
                                    Pair(categories[3], mappedCategories[categories[3]] ?: 0.0),
                                    Pair(categories[4], mappedCategories[categories[4]] ?: 0.0)
                                )
                            )
                        }
                    }
                }
                items(
                    expenses.sortedByDescending { it.date },
                    key = { it.id }) { expense -> //expenses.value
                    Divider()
                    Column(
                        Modifier
                            .padding()
                            .clip(Shapes.medium)
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding()
                                .background(
                                    BackgroundElevated
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Name: ${expense.note}", fontSize = 16.sp)
                                Spacer(modifier = Modifier.padding(12.dp))
                                Text(
                                    text = "Amount: $${expense.amount.toBigDecimal().setScale(2)}",
                                    fontSize = 16.sp
                                )
                            }
                            IconButton(
                                onClick = {
                                    selectedExpense = expense
                                    showDialog = true
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Expense")
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding()
                                .background(
                                    BackgroundElevated
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Category: ${expense.category}", fontSize = 12.sp)
                            Spacer(modifier = Modifier.padding(12.dp))
                            Text(text = "Date: ${expense.date}", fontSize = 12.sp)
                        }
                        Divider()
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this expense? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedExpense?.let {
                            viewModel.deleteExpense(it.id)
                        }
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    // THIS IS THE END
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun TransactionScreen(navController: NavController) {
//    val viewModel: ExpenseViewModel = viewModel()
//    Transaction(navController = navController, viewModel = viewModel)
//}

