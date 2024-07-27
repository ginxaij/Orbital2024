package com.example.wealthwings.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.model.CPFQuestions
import com.example.wealthwings.model.Question
import com.example.wealthwings.model.StockQuestions
import com.example.wealthwings.model.TVMQuestions

//@Composable
//fun QuizGamePage(navController: NavController, topic: String) {
//    var questionIndex by remember { mutableStateOf(0) }
//    var score by remember { mutableStateOf(0) }
//    var showScore by remember { mutableStateOf(false) }
//
//    if (showScore) {
//        ScoreScreen(score = score, totalQuestions = 10, navController = navController) {
//            questionIndex = 0
//            score = 0
//            showScore = false
//        }
//    } else if (topic == "CPF") {
//        QuestionScreen(
//            question = CPFQuestions[questionIndex],
//            onAnswerSelected = { selectedIndex ->
//                if (selectedIndex == CPFQuestions[questionIndex].correctAnswerIndex) {
//                    score++
//                }
//                if (questionIndex < CPFQuestions.size - 1) {
//                    questionIndex++
//                } else {
//                    showScore = true
//                }
//            }
//        )
//    } else if (topic == "TVM") {
//        QuestionScreen(
//            question = TVMQuestions[questionIndex],
//            onAnswerSelected = { selectedIndex ->
//                if (selectedIndex == TVMQuestions[questionIndex].correctAnswerIndex) {
//                    score++
//                }
//                if (questionIndex < TVMQuestions.size - 1) {
//                    questionIndex++
//                } else {
//                    showScore = true
//                }
//            }
//        )
//    } else if (topic == "stock") {
//        QuestionScreen(
//            question = StockQuestions[questionIndex],
//            onAnswerSelected = { selectedIndex ->
//                if (selectedIndex == StockQuestions[questionIndex].correctAnswerIndex) {
//                    score++
//                }
//                if (questionIndex < StockQuestions.size - 1) {
//                    questionIndex++
//                } else {
//                    showScore = true
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun QuestionScreen(question: Question, onAnswerSelected: (Int) -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(question.text, modifier = Modifier.padding(16.dp))
//        Spacer(modifier = Modifier.height(16.dp))
//        question.options.forEachIndexed{ index, option ->
//            Button(
//                onClick = {onAnswerSelected(index)},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//            ) {
//                Text(option)
//            }
//        }
//    }
//}
//
//@Composable
//fun ScoreScreen(score: Int, totalQuestions: Int, navController: NavController, onRestart: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("You scored $score out of $totalQuestions")
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = onRestart) {
//            Text("Restart")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { navController.navigate("quiz") }) {
//            Text("Back")
//        }
//    }
//}

@Composable
fun QuizGamePage(navController: NavController, topic: String) {
    var questionIndex by rememberSaveable { mutableStateOf(0) }
    var score by rememberSaveable { mutableStateOf(0) }
    var showScore by rememberSaveable { mutableStateOf(false) }
    var selectedAnswer by rememberSaveable { mutableStateOf<Int?>(null) }
    var correctAnswer by rememberSaveable { mutableStateOf<Boolean?>(null) }

    if (showScore) {
        ScoreScreen(score = score, totalQuestions = 10, navController = navController) {
            questionIndex = 0
            score = 0
            showScore = false
            selectedAnswer = null
            correctAnswer = null
        }
    } else {
        val question = when (topic) {
            "CPF" -> CPFQuestions[questionIndex]
            "TVM" -> TVMQuestions[questionIndex]
            "stock" -> StockQuestions[questionIndex]
            else -> CPFQuestions[0]
        }

        QuestionScreen(
            question = question,
            questionIndex = questionIndex,
            totalQuestions = CPFQuestions.size, // or TVMQuestions.size or StockQuestions.size based on topic
            selectedAnswer = selectedAnswer,
            correctAnswer = correctAnswer,
            onAnswerSelected = { selectedIndex ->
                selectedAnswer = selectedIndex
                correctAnswer = selectedIndex == question.correctAnswerIndex
                if (correctAnswer == true) {
                    score++
                }
            },
            onNextQuestion = {
                if (questionIndex < CPFQuestions.size - 1) {
                    questionIndex++
                    selectedAnswer = null
                    correctAnswer = null
                } else {
                    showScore = true
                }
            }
        )
    }
}

@Composable
fun QuestionScreen(
    question: Question,
    questionIndex: Int,
    totalQuestions: Int,
    selectedAnswer: Int?,
    correctAnswer: Boolean?,
    onAnswerSelected: (Int) -> Unit,
    onNextQuestion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question ${questionIndex + 1}/$totalQuestions",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = question.text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEachIndexed { index, option ->
            val backgroundColor = when {
                selectedAnswer == null -> Color.DarkGray
                index == selectedAnswer && correctAnswer == true -> Color(0xFF4CAF50)
                index == selectedAnswer && correctAnswer == false -> Color(0xFFF44336)
                index == question.correctAnswerIndex -> Color(0xFF4CAF50)
                else -> Color.Gray
            }

            val icon = when {
                index == selectedAnswer && correctAnswer == true -> Icons.Default.Check
                index == selectedAnswer && correctAnswer == false -> Icons.Default.Close
                else -> null
            }

            Button(
                onClick = {
                    if (selectedAnswer == null) {
                        onAnswerSelected(index)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer != null) {
            Button(
                onClick = onNextQuestion,
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Next Question")
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
        Card(

            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Quiz Completed!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "You scored $score out of $totalQuestions",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = onRestart,
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Restart")
                }
                Button(
                    onClick = { navController.navigate("quiz") },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Back")
                }
            }
        }
    }
}

