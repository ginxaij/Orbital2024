package com.example.wealthwings.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
//fun LoginScreen(navController: NavController) {
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    var loginError by remember { mutableStateOf("") }
//
//    // Adding padding and alignment for the entire screen
//        Surface(
//            modifier = Modifier
//                .padding()
//                .fillMaxSize(), color = Background) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//
//            ) {
//                Text("Welcome Back!", style = MaterialTheme.typography.headlineLarge)
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Email input field
//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text("Email") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//                Spacer(modifier = Modifier.height(0.dp))
//
//                // Password input field
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text("Password") },
//                    singleLine = true,
//                    visualTransformation = PasswordVisualTransformation(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//                Spacer(modifier = Modifier.height(0.dp))
//
//                // Login button
//                Button(
//                    onClick = {
//                        if (email.isBlank() || password.isBlank()) {
//                            loginError = "Email and Password cannot be empty"
//                        } else {
//                            Log.i("Info", "Attempt to log in")
//                            UserService.loginUser(email, password, onSuccess = {
//                                Log.i("Info", "Attempt successful")
//                                navController.navigate("transaction") {
//                                    popUpTo(Screen.Login.route) { inclusive = true }
//                                }
//                            }, onError = {
//                                loginError = it
//                            })
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Text("Login")
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Navigation to registration screen
//                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
//                    Text("No account? Register here", color = Primary)
//                }
//
//                // Display login errors
//                if (loginError.isNotEmpty()) {
//                    Text(
//                        "Error: $loginError",
//                        color = Color.Red,
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                }
//            }
//        }
//    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .padding()
            .fillMaxSize(), color = Background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Welcome Back!", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(0.dp))

            // Password input field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(0.dp))

            // Login button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        loginError = "Email and Password cannot be empty"
                    } else {
                        UserService.loginUser(email, password, onSuccess = {
                            navController.navigate("transaction") {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }, onError = {
                            loginError = it
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Navigation to registration screen
            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("No account? Register here", color = Primary)
            }

            // Display login errors
            if (loginError.isNotEmpty()) {
                Text(
                    "$loginError",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}