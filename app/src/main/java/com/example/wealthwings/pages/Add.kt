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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.wealthwings.model.Expense
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.ui.theme.TextPrimary
import com.example.wealthwings.viewmodels.ExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// test the test branch
fun Add(navController: NavController, viewModel: ExpenseViewModel) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("Select") }
    val categories = listOf(
        "Food & Dining", "Transportation", "Utilities",
        "Groceries", "Miscellaneous")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
                )
            )
        },
        content = { innerPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, //submiy expense to make it center
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
//                    OutlinedTextField(
//                        value = amount,
//                        onValueChange = { amount = it },
//                        label = { Text("Amount") },
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    TableRow(label = "Amount", detail = {
                        UnstyledTextField(
                            value = amount,
                            onValueChange = { text -> amount = text },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("0") },
                            arrangement = Arrangement.End,
                            maxLines = 1,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            )
                        )
                    })

                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(label = "Date", detail = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            DateFunction(
                                onDateSelected = { selectedDate ->
                                    date = selectedDate
                                },
                                initialDate = "Choose Date"
                            )
                        }
                    })
                    Divider(thickness = 1.dp, color = Divider)

                    TableRow(label = "Note", detail = {
                        UnstyledTextField(
                            value = note,
                            onValueChange = { newValue ->
                                if (newValue.length <= 25) {
                                    note = newValue
                                } else {
                                    note = newValue.take(25)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("0") },
                            arrangement = Arrangement.End,
                            maxLines = 1,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            keyboardOptions = KeyboardOptions(
                            )
                        )
                    })

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
                }

//                    Divider(thickness = 1.dp, color = Divider)
//                    TableRow(
//                        "Note"
//                    )
//                    Divider(thickness = 1.dp, color = Divider)
//                    TableRow(
//                        "Category"
//                    )
//                    Divider(thickness = 1.dp, color = Divider)

                Button(
                    onClick = {
                        if (amount.isNotBlank() && category.isNotBlank() && date.isNotBlank()) {
                            val expense = Expense(
                                //UUID(),
                                amount = amount.toDouble(),
                                category = category,
                                date = date,
                                note = note
                            )
                            viewModel.addExpense(expense)
                            navController.navigate("transaction") {
                                popUpTo("transaction") {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }, modifier = Modifier.padding(16.dp),
                    shape = Shapes.large) {
                    Text(text = "Submit Expenses")
                }
            }

        }
    )
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewAdd() {
//    WealthWingsTheme {
//        val navController = rememberNavController()
//        Add(navController = navController, viewModel = ExpenseViewModel())
//    }
//}