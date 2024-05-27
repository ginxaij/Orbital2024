package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.components.UnstyledTextField
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// test the test branch
fun Add(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
                )
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    TableRow("Amount", detail = {
                        UnstyledTextField()
                    //TextField("hello", onValueChange = {})
                    })

                    Divider(thickness = 1.dp, color = Divider)
                    TableRow("Recurrence")
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(
                        "Date")
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(
                        "Note")
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(
                        "Category")
                }
            }
        }
    )
}