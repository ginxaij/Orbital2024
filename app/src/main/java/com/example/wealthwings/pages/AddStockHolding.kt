package com.example.wealthwings.pages

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
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.components.UnstyledTextField
import com.example.wealthwings.model.StockHolding
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.StockHoldingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// test the test branch
fun AddStockHolding(navController: NavController, viewModel: StockHoldingViewModel) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
//    var expanded by remember { mutableStateOf(false) }
//    var category by remember { mutableStateOf("Select") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    TableRow(label = "Name", detail = {
                        UnstyledTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("") },
                            arrangement = Arrangement.End,
                            maxLines = 1,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right,
                            ),
                            keyboardOptions = KeyboardOptions(
                            )
                        )
                    })

                    Divider(thickness = 1.dp, color = Divider)

                    TableRow(label = "Price", detail = {
                        UnstyledTextField(
                            value = price,
                            onValueChange = { text -> price = text },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("") },
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

                    TableRow(label = "Quantity", detail = {
                        UnstyledTextField(
                            value = quantity,
                            onValueChange = { text -> quantity = text },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("") },
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
                }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && quantity.isNotBlank() && price.isNotBlank()) {
                                val stockHolding = StockHolding(
                                    //UUID(),
                                    name = name,
                                    price = price.toDouble(),
                                    quantity = quantity.toInt() ,

                                )
                                viewModel.addHolding(stockHolding)
                                navController.navigate("investment") {
                                    popUpTo("investment") {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }, modifier = Modifier.padding(16.dp),
                        shape = Shapes.large
                    ) {
                        Text(text = "Enter")
                    }
                }


        }
    )
}

