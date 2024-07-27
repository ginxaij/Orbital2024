package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.viewmodels.NewsViewModel

@Composable
fun NewsContent(navController: NavController, viewModel: NewsViewModel) {
    val content by viewModel.SelectedContent.observeAsState("")
    val headline by viewModel.SelectedHeadline.observeAsState("")
    val description by viewModel.SelectedDescription.observeAsState("")

    Scaffold(
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                }
                Text(headline, modifier = Modifier.padding(16.dp))
                Divider()
                Text(content, modifier = Modifier.padding(16.dp))
            }
        })
}