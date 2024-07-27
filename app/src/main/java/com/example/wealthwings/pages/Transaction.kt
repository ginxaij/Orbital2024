package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.model.Expense
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.ExpenseViewModel
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transaction(navController: NavController, viewModel: ExpenseViewModel) {
    val expenses by viewModel.expensesLiveData.observeAsState(emptyList())
    val totalAmount by viewModel.totalAmount.observeAsState(0.0)
    val startDate by viewModel.startDate.observeAsState()
    val endDate by viewModel.endDate.observeAsState()

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Expenses for ",
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            OutlinedButton(onClick = { navController.navigate("transaction/editdate") }) {
                                if (startDate == null || endDate == null) {
                                    Text(
                                        "All Days",
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                } else if (startDate == endDate) {
                                    Text(
                                        "%s".format(startDate.toString()),
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                } else {
                                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                    val newStartDate = startDate!!.format(formatter).toString().replace("-", "/")
                                    val newEndDate = endDate!!.format(formatter).toString().replace("-", "/")
                                    Text(
                                        "%s - %s".format(newStartDate, newEndDate),
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            "$${totalAmount.toBigDecimal().setScale(2)}",
                            fontSize = 30.sp,
                            color = Color.White
                        )

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
                                    Pair(categories[0], mappedCategories[categories[0]] ?: 0.0),
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
                    key = { it.id }
                ) { expense ->
                    ExpenseItem(
                        expense = expense,
                        onClick = {
                            navController.navigate("transaction/detail/${expense.id}")
                        }
                    )
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
}

@Composable
fun ExpenseItem(expense: Expense, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Top row: Category and Amount
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$${expense.amount.toBigDecimal().setScale(2)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Bottom row: Note and Date
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                expense.note?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = expense.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}




//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun TransactionScreen(navController: NavController) {
//    val viewModel: ExpenseViewModel = viewModel()
//    Transaction(navController = navController, viewModel = viewModel)
//}

