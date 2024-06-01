package com.example.wealthwings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wealthwings.Screens.LoginScreen
import com.example.wealthwings.Screens.MainScreen
import com.example.wealthwings.Screens.RegisterScreen
import com.example.wealthwings.Screens.Screen
import com.example.wealthwings.ui.theme.WealthWingsTheme
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            WealthWingsTheme {
                navController = rememberNavController()  // Use the class level navController
                NavHost(
                    navController = navController as NavHostController,
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) { LoginScreen(navController) }
                    composable(Screen.Register.route) { RegisterScreen(navController) }
                    composable(Screen.Main.route) { MainScreen() }
                }
            }
        }
    }
}


//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        updateUI(currentUser)  // Ensure this is called after navController is initialized
//    }

//    private fun updateUI(user: FirebaseUser?) {
//        if (user != null) {
//            navController?.let {
//                if (it.currentDestination?.route != Screen.Main.route) {
//                    it.navigate(Screen.Main.route) {
//                        popUpTo(Screen.Login.route) { inclusive = true }
//                    }
//                }
//            }
//        } else {
//            navController?.let {
//                if (it.currentDestination?.route != Screen.Login.route) {
//                    it.navigate(Screen.Login.route)
//                }
//            }
//        }
//    }
//}


