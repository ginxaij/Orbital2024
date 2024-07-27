package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavController
import com.example.wealthwings.Screens.Screen
import com.example.wealthwings.Screens.UserService
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.components.UnstyledTextField
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.Expense
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChangeEmail(navController: NavController) {
    var currentEmail by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newEmail2 by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier.padding(16.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                }
                Text(
                    text = "Change Email",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "Current Email", detail = {
                    UnstyledTextField(
                        value = currentEmail,
                        onValueChange = { text -> currentEmail = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        )
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "New Email", detail = {
                    UnstyledTextField(
                        value = newEmail,
                        onValueChange = { text -> newEmail = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        )
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "Retype new Email", detail = {
                    UnstyledTextField(
                        value = newEmail2,
                        onValueChange = { text -> newEmail2 = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        )
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "Enter password", detail = {
                    UnstyledTextField(
                        value = password,
                        onValueChange = { text -> password = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        )
                    )
                })
                Button(
                    onClick = {
                        if (currentEmail.isNotBlank() && newEmail.isNotBlank() && newEmail2.isNotBlank() &&
                            password.isNotBlank()) {
                            if (newEmail == newEmail2) {
                                if (currentEmail == FirebaseAuth.getInstance().currentUser!!.email.toString()) {
                                    if (password == FirebaseDB().readPassword()) {
                                        UserService.changeUserEmail(newEmail) { success, message ->
                                            if (success) {
                                                errorMessage = message
                                            } else {
                                                errorMessage = message
                                            }
                                        }
                                    } else {
                                        errorMessage = "Please check that you have entered the correct password!"
                                    }
                                } else {
                                    errorMessage = "Please check that you have entered the current email correctly!"
                                }
                            } else {
                                errorMessage = "Please check that you have entered the new email correctly!"
                            }
                        } else {
                            errorMessage = "Please check that you have not left any blanks!"
                        }
                    }, modifier = Modifier.padding(16.dp),
                    shape = Shapes.large) {
                    Text(text = "Confirm")
                }

                if (errorMessage == "Email sent.") {
                    showDialog = true
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Change Email") },
                        text = { Text("Please verify your change with the link sent to your new email! You will now be logged out.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) {
                                            inclusive = true
                                        }
                                    }
                                    FirebaseAuth.getInstance().signOut()
                                }
                            ) {
                                Text("Confirm")
                            }
                        }
                    )
                }
            }

        })
}

