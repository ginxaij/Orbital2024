package com.example.wealthwings.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wealthwings.Screens.Screen
import com.example.wealthwings.Screens.UserService
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DeleteUser(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

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
                    text = "Delete Account",
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
                    text = "Deleting your account will remove all your data permanently. This action cannot be undone.",
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = Shapes.large,
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirm Delete User") },
                    text = { Text("Are you sure you want to delete this account? This action is irreversible!") },
                    confirmButton = {
                        Button(
                            onClick = {
                                val auth: FirebaseAuth = FirebaseAuth.getInstance()
                                val user = auth.currentUser
                                val uid = user!!.uid
                                user!!.delete().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // User deleted
                                        FirebaseDB().deleteUser(uid)
                                        Log.d("DeleteUser", "User account deleted.")
                                        navController.navigate(Screen.Login.route)
                                    } else {
                                        // Deletion failed
                                        Log.e("DeleteUser", "Failed to delete user.", task.exception)
                                    }
                                }
                                showDialog = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        })
}