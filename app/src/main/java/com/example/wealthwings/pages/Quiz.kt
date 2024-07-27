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
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.example.wealthwings.R
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.ui.theme.SystemGray04
import com.kwabenaberko.newsapilib.NewsApiClient


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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("quiz/news") },
                shape = CircleShape,
                containerColor = SystemGray04
            ) {
                Icon(
                    painterResource(id = R.drawable.addicon),
                    contentDescription = "News")
            }
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