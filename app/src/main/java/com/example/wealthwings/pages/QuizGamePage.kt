package com.example.wealthwings.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.model.CPFQuestions
import com.example.wealthwings.model.Question
import com.example.wealthwings.model.StockQuestions
import com.example.wealthwings.model.TVMQuestions

@Composable
fun QuizGamePage(navController: NavController, topic: String) {
    var questionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var showScore by remember { mutableStateOf(false) }

    if (showScore) {
        ScoreScreen(score = score, totalQuestions = 10, navController = navController) {
            questionIndex = 0
            score = 0
            showScore = false
        }
    } else if (topic == "CPF") {
        QuestionScreen(
            question = CPFQuestions[questionIndex],
            onAnswerSelected = { selectedIndex ->
                if (selectedIndex == CPFQuestions[questionIndex].correctAnswerIndex) {
                    score++
                }
                if (questionIndex < CPFQuestions.size - 1) {
                    questionIndex++
                } else {
                    showScore = true
                }
            }
        )
    } else if (topic == "TVM") {
        QuestionScreen(
            question = TVMQuestions[questionIndex],
            onAnswerSelected = { selectedIndex ->
                if (selectedIndex == TVMQuestions[questionIndex].correctAnswerIndex) {
                    score++
                }
                if (questionIndex < TVMQuestions.size - 1) {
                    questionIndex++
                } else {
                    showScore = true
                }
            }
        )
    } else if (topic == "stock") {
        QuestionScreen(
            question = StockQuestions[questionIndex],
            onAnswerSelected = { selectedIndex ->
                if (selectedIndex == StockQuestions[questionIndex].correctAnswerIndex) {
                    score++
                }
                if (questionIndex < StockQuestions.size - 1) {
                    questionIndex++
                } else {
                    showScore = true
                }
            }
        )
    }
}

@Composable
fun QuestionScreen(question: Question, onAnswerSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(question.text, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        question.options.forEachIndexed{ index, option ->
            Button(
                onClick = {onAnswerSelected(index)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(option)
            }
        }
    }
}

@Composable
fun ScoreScreen(score: Int, totalQuestions: Int, navController: NavController, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("You scored $score out of $totalQuestions")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRestart) {
            Text("Restart")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("quiz") }) {
            Text("Back")
        }
    }
}