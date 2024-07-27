package com.example.wealthwings.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.Primary


//@Composable
//fun RegisterScreen(navController: NavController) {
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    var confirmPassword by rememberSaveable { mutableStateOf("") }
//    var registrationError by remember { mutableStateOf("") }
//
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(), color = Background
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Create Account", style = MaterialTheme.typography.headlineLarge)
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Email input field
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Password input field
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Confirm password input field
//            OutlinedTextField(
//                value = confirmPassword,
//                onValueChange = { confirmPassword = it },
//                label = { Text("Confirm Password") },
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Register button
//            Button(
//                onClick = {
//                    if (password == confirmPassword) {
//                        UserService.registerUser(email, password, onSuccess = {
//                            navController.navigate(Screen.Login.route) {
//                                popUpTo(Screen.Login.route) { inclusive = true }
//                            }
//                        }, onError = {
//                            registrationError = it
//                        })
//                    } else {
//                        registrationError = "Passwords do not match."
//                    }
//                },
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text("Register")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
//                Text("Have an account? Sign in here", color = Primary)
//            }
//
//            // Display registration errors
//            if (registrationError.isNotEmpty()) {
//                Text(
//                    "Error: $registrationError",
//                    color = Color.Red,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//        }
//    }
//}

@Composable
fun RegisterScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var registrationError by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(), color = Background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Password input field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Confirm password input field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Register button
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        UserService.registerUser(email, password, onSuccess = {
                            email = ""
                            password = ""
                            confirmPassword = ""
                            registrationError = ""
                            showSuccessMessage = true
                            showDialog = true
                        }, onError = {
                            registrationError = it
                            showSuccessMessage = false
                        })
                    } else {
                        registrationError = "Passwords do not match."
                        showSuccessMessage = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text("Have an account? Sign in here", color = Primary)
            }

            // Display registration errors
            if (registrationError.isNotEmpty()) {
                Text(
                    "Error: $registrationError",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Display success message
            if (showSuccessMessage) {
                Text(
                    "Registration successful! Please check your email for verification.",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Registration Successful") },
            text = { Text("Please check your email for verification.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}



