package com.example.wealthwings.Screens

import com.google.firebase.auth.FirebaseAuth

object UserService {
    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }

    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }
}