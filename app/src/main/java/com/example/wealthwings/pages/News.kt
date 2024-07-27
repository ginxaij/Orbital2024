package com.example.wealthwings.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.viewmodels.NewsViewModel
import com.kwabenaberko.newsapilib.models.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun News(navController: NavController, viewModel: NewsViewModel) {
    val articles by viewModel.articles.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    LaunchedEffect(Unit) {
        viewModel.getTopHeadlines()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Headlines", style = MaterialTheme.typography.headlineMedium, color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Background)
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.scale(3f))
                    }
                } else {
                    NewsList(articles, navController, viewModel)
                }
            }
        }
    )
}


@Composable
fun NewsList(articles: List<Article>, navController: NavController, viewModel: NewsViewModel) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(articles) { article ->
            ArticleItem(article, navController, viewModel)
        }
    }
}


@Composable
fun ArticleItem(article: Article, navController: NavController, viewModel: NewsViewModel) {
    val annotatedText = AnnotatedString.Builder()
        .apply {
            withStyle(style = SpanStyle(color = Color.White, fontSize = 16.sp)) {
                append(article.title)
            }
        }.toAnnotatedString()
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                viewModel.getContent(article)
                navController.navigate("quiz/news/newscontent")
            },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = annotatedText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

