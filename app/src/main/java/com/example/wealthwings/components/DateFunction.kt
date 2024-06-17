package com.example.wealthwings.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFunction(
    onDateSelected: (String) -> Unit,
    initialDate: String = "Choose Date"
) {
    var dateText by remember { mutableStateOf(initialDate) }
    val dateState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = dateText,
        style = MaterialTheme.typography.bodySmall,
        color = if (dateText == initialDate) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.clickable { showDialog = true }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        dateState.selectedDateMillis?.let {
                            val selectedDate = java.time.Instant.ofEpochMilli(it)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate().toString()
                            dateText = selectedDate
                            onDateSelected(selectedDate)
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = dateState, showModeToggle = true)
        }
    }
}
