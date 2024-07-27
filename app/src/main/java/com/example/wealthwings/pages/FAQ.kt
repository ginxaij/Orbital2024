package com.example.wealthwings.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.model.FAQItem
import com.example.wealthwings.ui.theme.Background

//import com.example.wealthwings.ui.theme.TopAppBarBackGround


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQ(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQ") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Background
                )
            )
        },

        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                FAQList(faqs = faqList)
            }
        })


}

val faqList = listOf(
    FAQItem("What is WealthWings?", "WealthWings is a comprehensive finance management app designed to help you track and manage your expenses and investments."),
    FAQItem("How do I get started with WealthWings?", "After downloading the app, sign up for an account"),
    FAQItem("How to add transactions?", "Select the + button and fill in the details of the transaction"),
    FAQItem("How to remove transactions?", "Select the bin logo next to the transaction you would like to remove"),
    FAQItem("How to remove all transactions?", "Select the 'Delete All' button in the More Menu"),
    FAQItem("How to filter my transactions by dates?", "Click into the date button in the transaction page and specify the period"),
    FAQItem("How do I add a new stock holding?", "Navigate to the Investment page and click on the 'Add' button. You can search for the stock and input the details."),
    FAQItem("Can I view my transaction history?", "Yes, you can view all your past transactions in the Transaction page."),
    FAQItem("What is the Quiz feature?", "The Quiz feature is designed to help you learn more about personal finance through interactive quizzes."),
    FAQItem("How can I update my profile?", "Go to the More page and select 'Profile'. You can update your personal information from there."),
    FAQItem("How do I set a budget?", "Navigate to the Budget page from the More section. You can set and track your monthly budget there."),

    // Add more FAQs here
)
@Composable
fun FAQItem(faqItem: FAQItem) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { expanded = !expanded }
        .background(color = Color.DarkGray)
        .padding(16.dp)) {

        Text(text = faqItem.question)
        Spacer(modifier = Modifier.height(2.dp))

        AnimatedVisibility(visible = expanded) {
            Text(text = faqItem.answer, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun FAQList(faqs: List<FAQItem>) {
    LazyColumn {
        items(faqs) { faq ->
            FAQItem(faqItem = faq)
        }
    }
}