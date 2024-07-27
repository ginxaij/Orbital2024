package com.example.wealthwings.Screens

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.wealthwings.db.FirebaseDB
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object UserService {

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }

    fun registerUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseDB().writeNewUser(FirebaseAuth.getInstance().currentUser!!.uid, password, email)
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "An unknown error occurred")
                }
            }
    }

    fun changeUserEmail(
        oldEmail: String,
        newEmail: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        val credential = EmailAuthProvider.getCredential(oldEmail, password)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.verifyBeforeUpdateEmail(newEmail)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    FirebaseDB().writeEmail(newEmail = newEmail)
                                    callback(true, "Email updated successfully.")
                                } else {
                                    callback(
                                        false,
                                        "Error updating Email: ${updateTask.exception?.message}"
                                    )
                                }
                            }
                    } else {
                        callback(
                            false,
                            "Re-authentication failed: ${reauthTask.exception?.message}"
                        )
                    }
                }
        } else {
            callback(false, "No authenticated user found.")
        }
    }

    fun verifyUserEmail(
        oldEmail: String,
        newEmail: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.verifyBeforeUpdateEmail(newEmail)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Verification email sent!
                Log.d("EmailVerification", "Verification email sent.")
            } else {
                // Handle errors
                val e = task.exception
                Log.e("EmailVerification", "Error sending verification email", e)
            }
        }
            ?: Log.e("EmailVerification", "No user is currently signed in.")
    }

    fun changeUserPassword(
        currentPassword: String,
        newPassword: String,
        callback: (Boolean, String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            val credential = EmailAuthProvider.getCredential(user.email.toString(), currentPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    FirebaseDB().writePassword(newPassword = newPassword)
                                    callback(true, "Password updated successfully.")
                                } else {
                                    callback(
                                        false,
                                        "Error updating password: ${updateTask.exception?.message}"
                                    )
                                }
                            }
                    } else {
                        callback(
                            false,
                            "Re-authentication failed: ${reauthTask.exception?.message}"
                        )
                    }
                }
        } else {
            callback(false, "No authenticated user found.")
        }
    }

    fun changeUserEmail(newEmail: String, callback: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            it.verifyBeforeUpdateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, "Email sent.")
                        // Start polling to check if the email has been verified and updated

                    } else {
                        callback(false, "Error updating email: ${task.exception?.message}")
                    }
                }
        } ?: run {
            callback(false, "User is not authenticated")
        }
    }

}
