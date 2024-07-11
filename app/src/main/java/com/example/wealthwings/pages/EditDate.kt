@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wealthwings.pages

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.components.DateFunction
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.ExpenseViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDate(navController: NavController, expenseViewModel: ExpenseViewModel) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Date") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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
                    TableRow(label = "Start Date", detail = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            DateFunction(
                                onDateSelected = { selectedDate ->
                                    startDate = selectedDate
                                    Log.i("Info", selectedDate)
                                },
                                initialDate = "Choose Date"
                            )
                        }
                    })
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(label = "End Date", detail = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            DateFunction(
                                onDateSelected = { selectedDate ->
                                    endDate = selectedDate
                                    Log.i("Info", selectedDate)
                                },
                                initialDate = "Choose Date"
                            )
                        }
                    })
                }
                Button(onClick = { if (startDate.isNotBlank() || endDate.isNotBlank() ) {
                    expenseViewModel.setStartAndEndDate(LocalDate.parse(startDate),
                        LocalDate.parse(endDate))
                    navController.navigate("transaction")
                }
                }) {
                    Text("Confirm")
                }

                Button(onClick = {
                    expenseViewModel.setStartAndEndDate(null, null)
                    navController.navigate("transaction")
                }) {
                    Text("Reset")
                }
            }
        })
}