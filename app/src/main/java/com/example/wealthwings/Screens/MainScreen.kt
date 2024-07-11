package com.example.wealthwings.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wealthwings.R
import com.example.wealthwings.pages.Add
import com.example.wealthwings.pages.AddStockHolding
import com.example.wealthwings.pages.EditDate
import com.example.wealthwings.pages.Investment
import com.example.wealthwings.pages.More
import com.example.wealthwings.pages.Quiz
import com.example.wealthwings.pages.QuizGamePage
import com.example.wealthwings.pages.Transaction
import com.example.wealthwings.ui.theme.BottomBar
import com.example.wealthwings.viewmodels.ExpenseViewModel
import com.example.wealthwings.viewmodels.StockHoldingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController2: NavHostController, expenseViewModel: ExpenseViewModel, stockHoldingViewModel: StockHoldingViewModel) {//, searchViewModel: StockSearchViewModel) {
//    val navController =
//        rememberNavController() //this is to create an instance to manage app navigation within NavHost

    val navController = rememberNavController()
    val backStackEntry =
        navController.currentBackStackEntryAsState() //keep track of current route and its state,
    // to update the UI based on its state
    val showBottomBar = remember { mutableStateOf(true) }


    Scaffold(
        bottomBar = {
            if (showBottomBar.value) {
                NavigationBar(containerColor = BottomBar) {
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("transaction")
                            ?: false,
                        onClick = { navController.navigate("transaction") },
                        label = {
                            Text(
                                "Transaction",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.transactionicon),
                                contentDescription = "Transaction"
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("investment")
                            ?: false,
                        onClick = { navController.navigate("investment") },
                        label = {
                            Text(
                                "Investment",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.investmenticon),
                                contentDescription = "Investment"
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("quiz")
                            ?: false,
                        onClick = { navController.navigate("quiz") },
                        label = { Text("Quiz", style = MaterialTheme.typography.titleMedium) },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.quizicon),
                                contentDescription = "Quiz"
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("more")
                            ?: false,
                        onClick = { navController.navigate("more") },
                        label = { Text("More", style = MaterialTheme.typography.titleMedium) },
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.moreicon),
                                contentDescription = "More"
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                showBottomBar.value = false
                LoginScreen(navController)
            }
            composable(Screen.Register.route) {
                showBottomBar.value = false
                RegisterScreen(navController)
            }
            composable(Screen.Main.route) {
                showBottomBar.value = true
                MainScreen(navController, expenseViewModel, stockHoldingViewModel)
            }
            composable("transaction") {
                showBottomBar.value = true
                Transaction(navController, expenseViewModel)
            }
            composable("investment") {
                showBottomBar.value = true
                Investment(navController, stockHoldingViewModel)
            }
            composable("quiz") {
                showBottomBar.value = true
                Quiz(navController, "Quiz")
            }
            composable("more") {
                showBottomBar.value = true
                More(navController, expenseViewModel, stockHoldingViewModel)
            }
            composable("transaction/add") {
                showBottomBar.value = true
                Add(navController, expenseViewModel)
            }

            composable("transaction/editdate") {
                showBottomBar.value = true
                EditDate(navController, expenseViewModel)
            }

            composable("investment/addstockholding") {
                showBottomBar.value = true
                AddStockHolding(navController, stockHoldingViewModel)
            }
            composable("quiz/CPF") {
                showBottomBar.value = true
                QuizGamePage(navController, "CPF")
            }
            composable("quiz/TVM") {
                showBottomBar.value = true
                QuizGamePage(navController, "TVM")
            }
            composable("quiz/stock") {
                showBottomBar.value = true
                QuizGamePage(navController, "stock")
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainScreen() {
//    val navController =
//        rememberNavController() //this is to create an instance to manage app navigation within NavHost
//    val backStackEntry =
//        navController.currentBackStackEntryAsState() //keep track of current route and its state,
//    // to update the UI based on its stat
//    Scaffold(
//        bottomBar = {
//            NavigationBar(containerColor = BottomBar) {
//                NavigationBarItem(
//                    selected = backStackEntry.value?.destination?.route?.startsWith(
//                        "transaction"
//                    )
//                        ?: false, //app will crash if not selected (to track the state)
//                    onClick = { navController.navigate("transaction") },
//                    label = {
//                        Text(
//                            "Transaction",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    },
//
//                    icon = {
//                        Icon(
//                            painterResource(id = R.drawable.transactionicon),
//                            contentDescription = "Transaction"
//                        )
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = backStackEntry.value?.destination?.route == "investment",
//                    onClick = { navController.navigate("investment") },
//                    label = {
//                        Text(
//                            "Investment",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    },
//
//                    icon = {
//                        Icon(
//                            painterResource(id = R.drawable.investmenticon),
//                            contentDescription = "Investment"
//                        )
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = backStackEntry.value?.destination?.route == "quiz",
//                    onClick = { navController.navigate("quiz") },
//                    label = {
//                        Text("Quiz", style = MaterialTheme.typography.titleMedium)
//                    },
//
//                    icon = {
//                        Icon(
//                            painterResource(id = R.drawable.quizicon),
//                            contentDescription = "Quiz"
//                        )
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = backStackEntry.value?.destination?.route?.startsWith(
//                        "more"
//                    )
//                        ?: false, //if route selected starts with more, more will still be selected, else not selected
//                    onClick = { navController.navigate("more") },
//                    label = {
//                        Text("More", style = MaterialTheme.typography.titleMedium)
//                    },
//
//                    icon = {
//                        Icon(
//                            painterResource(id = R.drawable.moreicon),
//                            contentDescription = "More"
//                        )
//                    }
//                )
//
//            }
//        },
//
//        content = { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = "transaction"
//            ) {
//                composable("transaction") {
//                    Surface(
//                        //only for design puposes
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) { //val viewModel: ExpenseViewModel = viewModel()
//                        TransactionScreen(navController)
//                    }
//                }
//                composable("investment") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        Investment(navController,"Investment")
//                    }
//                }
//                composable("quiz") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        Quiz(navController, "Quiz")
//                    }
//                }
//                composable("more") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        More(navController)
//                    }
//                }
//
//                composable("more/myprofile") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        Profile(navController, "Profile")
//                    }
//                }
//
//                composable("more/FAQ") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                        FAQ(navController, "FAQ")
//                    }
//                }
//
//                composable("transaction/add") {
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) { val viewModel: ExpenseViewModel = viewModel()
//                        Add(navController, viewModel)
//                    }
//                }
//            }
//        }
//    )
//}