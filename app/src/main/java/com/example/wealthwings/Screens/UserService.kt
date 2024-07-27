package com.example.wealthwings.Screens

import android.util.Log
import com.example.wealthwings.db.FirebaseDB
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

//object UserService {
//    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    onSuccess()
//                } else {
//                    onError(task.exception?.message ?: "An unknown error occurred")
//                }
//            }
//    }
//
//    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    writeNewUser(FirebaseAuth.getInstance().currentUser!!.uid, password, email)
//                    onSuccess()
//                } else {
//                    onError(task.exception?.message ?: "An unknown error occurred")
//                }
//            }
//    }
//}

object UserService {
    private val auth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(email.substringBefore('@'))
                            .build()
                        it.updateProfile(profileUpdates).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                sendVerificationEmail(onSuccess, onError)
                            } else {
                                onError(task.exception?.message ?: "Profile update failed")
                            }
                        }
                    }
                } else {
                    onError(task.exception?.message ?: "Registration failed")
                }
            }
    }

    private fun sendVerificationEmail(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Failed to send verification email")
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        onSuccess()
                    } else {
                        onError("Please verify your email first")
                    }
                } else {
                    onError(task.exception?.message ?: "Login failed")
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

