package com.example.wealthwings.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.Background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quiz(navController: NavController, name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },

        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

                Button(onClick = {navController.navigate("quiz/CPF") },
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Text(text = "CPF")
                }

                Button(onClick = {navController.navigate("quiz/TVM")},
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Text(text = "Time Value of Money")
                }

                Button(onClick = {navController.navigate("quiz/stock") },
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Text(text = "Stock Market")
                }
            }

        })
}