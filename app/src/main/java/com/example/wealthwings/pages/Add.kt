package com.example.wealthwings.pages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wealthwings.model.Expense
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.ui.theme.WealthWingsTheme
import com.example.wealthwings.viewmodels.ExpenseViewModel
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// test the test branch
fun Add(navController: NavController, viewModel: ExpenseViewModel) {
    var amount by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
                )
            )
        },
        content = { innerPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

//                    TableRow(label = "Amount", detail = {
//                        UnstyledTextField(
//                            value = "Amount",
//                            onValueChange = { amount = it},
//                            modifier = Modifier.fillMaxWidth(),
//                            placeholder = { Text("0") },
//                            arrangement = Arrangement.End,
//                            maxLines = 1,
//                            textStyle = TextStyle(
//                                textAlign = TextAlign.Right,
//                            ),
//                            keyboardOptions = KeyboardOptions(
//                                keyboardType = KeyboardType.Number,
//                            )
//                        )
//                    })
                    Divider(thickness = 1.dp, color = Divider)
                    OutlinedTextField(
                        value = id,
                        onValueChange = { id = it },
                        label = { Text("Note") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
//                    TableRow(label = "Note", detail = {
//                        UnstyledTextField(
//                            value = "lll",
//                            onValueChange = { id = it},
//                            modifier = Modifier.fillMaxWidth(),
//                            placeholder = { Text("0") },
//                            arrangement = Arrangement.End,
//                            maxLines = 1,
//                            textStyle = TextStyle(
//                                textAlign = TextAlign.Right,
//                            ),
//                            keyboardOptions = KeyboardOptions(
//                            )
//                        )
//                    })

//                    TableRow("Recurrence", detail = {
//                        TextButton(onClick = { /*TODO*/ }) {
//                            Text(text = "Select")
//                            DropdownMenu(expanded = false , onDismissRequest = { /*TODO*/ }) {
//                            }
//                        }
//                    })
//                    Divider(thickness = 1.dp, color = Divider)
//                    TableRow(
//                        "Date"
//                    )
//                    Divider(thickness = 1.dp, color = Divider)
//                    TableRow(
//                        "Note"
//                    )
//                    Divider(thickness = 1.dp, color = Divider)
//                    TableRow(
//                        "Category"
//                    )
//                    Divider(thickness = 1.dp, color = Divider)
                }
                Button(onClick = {
                    if (amount.isNotBlank() && id.isNotBlank()) {
                        viewModel.addExpense(
                            Expense(
                                UUID.randomUUID().toString(),
                                amount.toDouble(),
                                "Category",
                                LocalDate.now(),
                                id
                            )
                        )
                        navController.navigate("transaction") {
                            popUpTo("transaction")
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
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAdd() {
    WealthWingsTheme {
        val navController = rememberNavController()
        Add(navController = navController, viewModel = ExpenseViewModel())
    }
}