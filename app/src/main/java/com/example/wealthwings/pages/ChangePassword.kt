package com.example.wealthwings.pages

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.Screens.Screen
import com.example.wealthwings.Screens.UserService
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.components.UnstyledTextField
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ChangePassword(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var newPassword2 by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                }
                Text(
                    text = "Change Password",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Your password must be at least 6 characters long.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "Current Password:", detail = {
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
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "New Password:", detail = {
                    UnstyledTextField(
                        value = newPassword,
                        onValueChange = { text -> newPassword = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                TableRow(label = "Retype new Password:", detail = {
                    UnstyledTextField(
                        value = newPassword2,
                        onValueChange = { text -> newPassword2 = text },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(" ") },
                        arrangement = Arrangement.End,
                        maxLines = 1,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )
                })
                Divider(thickness = 1.dp, color = Divider)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (password.isNotBlank() && newPassword.isNotBlank() && newPassword2.isNotBlank()) {
                                if (newPassword == newPassword2) { // use log to check
                                    UserService.changeUserPassword(
                                        password,
                                        newPassword
                                    ) { success, message ->
                                        if (success) {
                                            //errorMessage = message
                                            showDialog = true
                                        } else {
                                            errorMessage = message
                                        }
                                    }
                                } else {
                                    errorMessage = "Please check that you have entered the new password correctly!"
                                }
                            } else {
                                errorMessage = "Please check that you have not left any blanks!"
                            }

                        },
                        modifier = Modifier.padding(16.dp),
                        shape = Shapes.large,
                    ) {
                        Text(text = "Confirm")
                    }
                }

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Change Password") },
                        text = { Text("Your password has been successfully updated!") },
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

