package com.example.WealthWings.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.WealthWings.ui.theme.TopAppBarBackGround

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun More(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("More") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackGround
                )
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Hello More")
            }
        }
    )
}