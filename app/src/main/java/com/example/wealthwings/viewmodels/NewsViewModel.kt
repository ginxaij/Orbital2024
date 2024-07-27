package com.example.wealthwings.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse


class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val apiKey = "25a7e83717684cb78c39ab34930930fe"
    val category = "business"
    val newsApiClient = NewsApiClient(apiKey)
    var articles: MutableLiveData<List<Article>> =  MutableLiveData(emptyList<Article>())
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    var SelectedHeadline: MutableLiveData<String> = MutableLiveData("")
    var SelectedDescription: MutableLiveData<String> = MutableLiveData("")
    var SelectedContent: MutableLiveData<String> = MutableLiveData("")

    @Composable
    fun getTopHeadlines() {
        LaunchedEffect(Unit) {
            newsApiClient.getTopHeadlines(
                TopHeadlinesRequest.Builder()
                    .category(category)
                    .language("en")
                    .build(),
                object : NewsApiClient.ArticlesResponseCallback {
                    override fun onSuccess(response: ArticleResponse) {
                        articles.value = response.articles
                        isLoading.value = false
                    }

                    override fun onFailure(throwable: Throwable) {
                        println(throwable.message)
                        isLoading.value = false
                    }
                }
            )
        }
    }

    fun getContent(article: Article) {
        SelectedHeadline.value = article.title ?: "No Title"
        SelectedDescription.value = article.title ?: "No Description"
        SelectedContent.value = article.content ?: "No Content"
    }
}