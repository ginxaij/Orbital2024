package com.example.wealthwings.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated

//import com.example.wealthwings.ui.theme.TopAppBarBackGround


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController, name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },

        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(text = "hi Profile")

            }

        })


}