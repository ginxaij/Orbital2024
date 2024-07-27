package com.example.wealthwings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.components.DateFunction
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.components.UnstyledTextField
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.ui.theme.TextPrimary
import com.example.wealthwings.viewmodels.ExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailScreen(navController: NavController, expenseId: String, viewModel: ExpenseViewModel) {
    val expenseLiveData = viewModel.getExpenseById(expenseId)
    val expense by expenseLiveData.observeAsState()

    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Food & Dining", "Transportation", "Utilities", "Groceries", "Miscellaneous")

    LaunchedEffect(expense) {
        expense?.let {
            amount = it.amount.toString()
            note = it.note ?: ""
            date = it.date
            category = it.category
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Expense") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },
        content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)
            ) {
                if (expense != null) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .clip(Shapes.medium)
                            .background(BackgroundElevated)
                            .fillMaxWidth()
                    ) {
                        TableRow(label = "Amount", detail = {
                            UnstyledTextField(
                                value = amount,
                                onValueChange = { text -> amount = text },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("0") },
                                arrangement = Arrangement.End,
                                maxLines = 1,
                                textStyle = TextStyle(textAlign = TextAlign.Right),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        })

                        Divider(thickness = 1.dp, color = Divider)
                        TableRow(label = "Date", detail = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                DateFunction(
                                    onDateSelected = { selectedDate -> date = selectedDate },
                                    initialDate = date
                                )
                            }
                        })
                        Divider(thickness = 1.dp, color = Divider)

                        TableRow(label = "Category", detail = {
                            Column(Modifier.clickable { expanded = true }) {
                                Text(text = category, color = TextPrimary)
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    Modifier.background(BackgroundElevated)
                                ) {
                                    categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(text = cat) },
                                            onClick = {
                                                category = cat
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        })

                        TableRow(label = "Note", detail = {
                            UnstyledTextField(
                                value = note,
                                onValueChange = { newValue -> note = newValue.take(25) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("") },
                                arrangement = Arrangement.End,
                                maxLines = 1,
                                textStyle = TextStyle(textAlign = TextAlign.Right),
                                keyboardOptions = KeyboardOptions()
                            )
                        })
                    }

                    Button(
                        onClick = {
                            if (amount.isNotBlank() && category.isNotBlank() && date.isNotBlank()) {
                                val updatedExpense = expense!!.copy(
                                    amount = amount.toDouble(),
                                    category = category,
                                    date = date,
                                    note = note
                                )
                                viewModel.updateExpense(updatedExpense)
                                navController.navigate("transaction") {
                                    popUpTo("transaction") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }, modifier = Modifier.padding(16.dp),
                        shape = Shapes.large
                    ) {
                        Text(text = "Save Changes")
                    }

                    Button(
                        onClick = {
                            viewModel.deleteExpense(expense!!.id)
                            navController.navigate("transaction") {
                                popUpTo("transaction") { inclusive = true }
                                launchSingleTop = true
                            }
                        }, modifier = Modifier.padding(16.dp),
                        shape = Shapes.large
                    ) {
                        Text(text = "Delete Expense")
                    }
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    )
}
