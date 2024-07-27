package com.example.wealthwings.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.R
import com.example.wealthwings.ui.theme.Background


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Quiz(navController: NavController, name: String) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Quiz") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = Background
//                )
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { navController.navigate("quiz/news") },
//                shape = CircleShape,
//                containerColor = SystemGray04
//            ) {
//                Icon(
//                    painterResource(id = R.drawable.addicon),
//                    contentDescription = "News")
//            }
//        },
//
//        content = { innerPadding ->
//            Column(modifier = Modifier.padding(innerPadding)) {
//
//                Button(onClick = {navController.navigate("quiz/CPF") },
//                    modifier = Modifier.fillMaxWidth().padding(20.dp)
//                ) {
//                    Text(text = "CPF")
//                }
//
//                Button(onClick = {navController.navigate("quiz/TVM")},
//                    modifier = Modifier.fillMaxWidth().padding(20.dp)
//                ) {
//                    Text(text = "Time Value of Money")
//                }
//
//                Button(onClick = {navController.navigate("quiz/stock") },
//                    modifier = Modifier.fillMaxWidth().padding(20.dp)
//                ) {
//                    Text(text = "Stock Market")
//                }
//            }
//
//        })
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quiz(navController: NavController, name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz", style = MaterialTheme.typography.headlineMedium, color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
            )
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { navController.navigate("quiz/news") },
//                shape = CircleShape,
//                containerColor = SystemGray04
//            ) {
//                Icon(
//                    painterResource(id = R.drawable.news_icon),
//                    contentDescription = "News",
//                    modifier = Modifier.size(36.dp)
//                )
//            }
//        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // User greeting
                Text(
                    text = "Hi $name",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Let's make this day productive",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quiz buttons
                QuizCategoryCard(navController, "CPF", R.drawable.cpf_icon, "quiz/CPF")
                QuizCategoryCard(navController, "Time Value of Money", R.drawable.tvm_icon, "quiz/TVM")
                QuizCategoryCard(navController, "Stock Market", R.drawable.stock_icon, "quiz/stock")
                QuizCategoryCard(navController, "News", R.drawable.news_icon, "quiz/news")
            }
        }
    )
}

@Composable
fun QuizCategoryCard(navController: NavController, title: String, iconRes: Int, route: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(route) }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}