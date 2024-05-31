package com.example.wealthwings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun UnstyledTextField() {
    var text by remember { mutableStateOf("Hello") }

    Column {
        BasicTextField(value = text, onValueChange = {
            text = it
        })
        Text("The textfield has this text: " + text)
    }
}