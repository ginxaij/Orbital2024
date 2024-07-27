package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsContent(navController: NavController, viewModel: NewsViewModel) {
    val content by viewModel.SelectedContent.observeAsState("")
    val headline by viewModel.SelectedHeadline.observeAsState("")
    val description by viewModel.SelectedDescription.observeAsState("")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Content", style = MaterialTheme.typography.headlineMedium, color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .background(Background)
                .fillMaxSize()
                .padding(16.dp)) {
                Text(
                    text = headline,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Divider(color = Color.Gray, thickness = 1.dp)
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        })
}