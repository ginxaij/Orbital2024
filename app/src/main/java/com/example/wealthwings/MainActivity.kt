package com.example.wealthwings

//import com.example.wealthwings.viewmodels.StockSearchViewModel
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.wealthwings.Screens.MainScreen
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.ui.theme.WealthWingsTheme
import com.example.wealthwings.viewmodels.CompanyFinancialsViewModel
import com.example.wealthwings.viewmodels.ExpenseViewModel
import com.example.wealthwings.viewmodels.NewsViewModel
import com.example.wealthwings.viewmodels.StockHoldingViewModel
import com.example.wealthwings.viewmodels.StockSearchViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        auth = FirebaseAuth.getInstance()

        setContent {
            WealthWingsTheme {
                val navController = rememberNavController()
                val expenseViewModel: ExpenseViewModel = hiltViewModel()
                val stockHoldingViewModel: StockHoldingViewModel = hiltViewModel()
                val stockSearchViewModel: StockSearchViewModel = hiltViewModel()
                val companyFinancialsViewModel: CompanyFinancialsViewModel = hiltViewModel()
                val newsViewModel: NewsViewModel = hiltViewModel()
                val authListener = FirebaseAuth.AuthStateListener { auth ->
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        expenseViewModel.setCurrentUser(currentUser.uid)
                        stockHoldingViewModel.setCurrentUser(currentUser.uid)
                        if (auth.currentUser!!.email != FirebaseDB().readEmail()) {
                            FirebaseDB().writeEmail(newEmail = auth.currentUser!!.email.toString())
                        }
                        Log.d(TAG, "User is signed in. UID: ${currentUser.uid}")
                    } else {
                        // No user is signed in, handle accordingly
                        expenseViewModel.setCurrentUser(null)
                        Log.d(TAG, "No user signed in.")
                    }
                }

                // Start listening for authentication state changes
                auth.addAuthStateListener(authListener)

//                val showBottomBar = remember { mutableStateOf(true) }

                MainScreen(navController, expenseViewModel, stockHoldingViewModel, stockSearchViewModel, companyFinancialsViewModel,
                    newsViewModel)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        auth.removeAuthStateListener(authListener)
        FirebaseAuth.getInstance().signOut()
    }

}



//class MainActivity : ComponentActivity() {
//    private lateinit var auth: FirebaseAuth
//    private lateinit var navController: NavController
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidThreeTen.init(this)
//        enableEdgeToEdge()
//        super.onCreate(savedInstanceState)
//        auth = FirebaseAuth.getInstance()
//
//        setContent {
//            WealthWingsTheme {
//                navController = rememberNavController()  // Use the class level navController
//                NavHost(
//                    navController = navController as NavHostController,
//                    startDestination = Screen.Login.route
//                ) {
//                    composable(Screen.Login.route) { LoginScreen(navController) }
//                    composable(Screen.Register.route) { RegisterScreen(navController) }
//                    composable(Screen.Main.route) { MainScreen() }
//                }
//            }
//        }
//    }
//}


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


