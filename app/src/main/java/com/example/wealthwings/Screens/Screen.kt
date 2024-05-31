package com.example.wealthwings.Screens

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    // Add other routes as needed
}
