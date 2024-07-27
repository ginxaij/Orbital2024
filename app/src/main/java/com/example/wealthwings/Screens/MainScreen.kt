package com.example.wealthwings.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wealthwings.R
import com.example.wealthwings.pages.Add
import com.example.wealthwings.pages.AddStockHolding
import com.example.wealthwings.pages.ChangeEmail
import com.example.wealthwings.pages.ChangePassword
import com.example.wealthwings.pages.CompanyDetailsScreen
import com.example.wealthwings.pages.DeleteUser
import com.example.wealthwings.pages.EditDate
import com.example.wealthwings.pages.FAQ
import com.example.wealthwings.pages.Investment
import com.example.wealthwings.pages.More
import com.example.wealthwings.pages.News
import com.example.wealthwings.pages.NewsContent
import com.example.wealthwings.pages.Profile
import com.example.wealthwings.pages.Quiz
import com.example.wealthwings.pages.QuizGamePage
import com.example.wealthwings.pages.Transaction

import com.example.wealthwings.ui.theme.BottomBar
import com.example.wealthwings.viewmodels.CompanyFinancialsViewModel
import com.example.wealthwings.viewmodels.ExpenseViewModel
import com.example.wealthwings.viewmodels.NewsViewModel
import com.example.wealthwings.viewmodels.StockHoldingViewModel
import com.example.wealthwings.viewmodels.StockSearchViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController2: NavHostController, expenseViewModel: ExpenseViewModel, stockHoldingViewModel: StockHoldingViewModel, stockSearchViewModel: StockSearchViewModel, companyFinancialsViewModel: CompanyFinancialsViewModel,
               newsViewModel: NewsViewModel) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val showBottomBar = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (showBottomBar.value) {
                NavigationBar(containerColor = BottomBar) {
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("transaction") ?: false,
                        onClick = { navController.navigate("transaction") },
                        label = { Text("Transaction", style = MaterialTheme.typography.titleMedium) },
                        icon = { Icon(painterResource(id = R.drawable.transactionicon), contentDescription = "Transaction") }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("investment") ?: false,
                        onClick = { navController.navigate("investment") },
                        label = { Text("Investment", style = MaterialTheme.typography.titleMedium) },
                        icon = { Icon(painterResource(id = R.drawable.investmenticon), contentDescription = "Investment") }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("quiz") ?: false,
                        onClick = { navController.navigate("quiz") },
                        label = { Text("Quiz", style = MaterialTheme.typography.titleMedium) },
                        icon = { Icon(painterResource(id = R.drawable.quizicon), contentDescription = "Quiz") }
                    )
                    NavigationBarItem(
                        selected = backStackEntry.value?.destination?.route?.startsWith("more") ?: false,
                        onClick = { navController.navigate("more") },
                        label = { Text("More", style = MaterialTheme.typography.titleMedium) },
                        icon = { Icon(painterResource(id = R.drawable.moreicon), contentDescription = "More") }
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
                MainScreen(navController, expenseViewModel, stockHoldingViewModel, stockSearchViewModel, companyFinancialsViewModel, newsViewModel)
            }
            composable("transaction") {
                showBottomBar.value = true
                Transaction(navController, expenseViewModel)
            }
            composable("investment") {
                showBottomBar.value = true
                Investment(navController, stockHoldingViewModel, stockSearchViewModel)
            }
            composable("quiz") {
                showBottomBar.value = true
                Quiz(navController, "Quiz")
            }
            composable("more") {
                showBottomBar.value = true
                More(navController, expenseViewModel, stockHoldingViewModel)
            }
            composable("more/faq") {
                showBottomBar.value = true
                FAQ(navController)
            }
            composable("more/myprofile") {
                showBottomBar.value = true
                Profile(navController)
            }
            composable("more/myprofile/changeemail") {
                showBottomBar.value = true
                ChangeEmail(navController)
            }
            composable("more/myprofile/changepassword") {
                showBottomBar.value = true
                ChangePassword(navController)
            }
            composable("more/myprofile/deleteuser") {
                showBottomBar.value = true
                DeleteUser(navController)
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
                AddStockHolding(navController, stockHoldingViewModel, stockSearchViewModel)
            }
            composable("quiz/news") {
                showBottomBar.value = true
                News(navController, newsViewModel)
            }
            composable("quiz/news/newscontent") {
                showBottomBar.value = true
                NewsContent(navController, newsViewModel)
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
            composable("transaction/editdate") {
                showBottomBar.value = true
                EditDate(navController, expenseViewModel)
            }

            composable(
                "companyDetails/{symbol}",
                arguments = listOf(navArgument("symbol") { type = NavType.StringType })
            ) { backStackEntry ->
                showBottomBar.value = true
                val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
                CompanyDetailsScreen(navController, companyFinancialsViewModel, symbol)
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainScreen(navController2: NavHostController, expenseViewModel: ExpenseViewModel, stockHoldingViewModel: StockHoldingViewModel, stockSearchViewModel: StockSearchViewModel, companyFinancialsViewModel: CompanyFinancialsViewModel) {//, searchViewModel: StockSearchViewModel) {
////    val navController =
////        rememberNavController() //this is to create an instance to manage app navigation within NavHost
//
//    val navController = rememberNavController()
//    val backStackEntry =
//        navController.currentBackStackEntryAsState() //keep track of current route and its state,
//    // to update the UI based on its state
//    val showBottomBar = remember { mutableStateOf(true) }
////    val expenseViewModel: ExpenseViewModel = hiltViewModel()
////    val stockHoldingViewModel: StockHoldingViewModel = hiltViewModel()
////    val searchViewModel: StockSearchViewModel = hiltViewModel()
//
//
//    Scaffold(
//        bottomBar = {
//            if (showBottomBar.value) {
//                NavigationBar(containerColor = BottomBar) {
//                    NavigationBarItem(
//                        selected = backStackEntry.value?.destination?.route?.startsWith("transaction")
//                            ?: false,
//                        onClick = { navController.navigate("transaction") },
//                        label = {
//                            Text(
//                                "Transaction",
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                        },
//                        icon = {
//                            Icon(
//                                painterResource(id = R.drawable.transactionicon),
//                                contentDescription = "Transaction"
//                            )
//                        }
//                    )
//                    NavigationBarItem(
//                        selected = backStackEntry.value?.destination?.route?.startsWith("investment")
//                            ?: false,
//                        onClick = { navController.navigate("investment") },
//                        label = {
//                            Text(
//                                "Investment",
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                        },
//                        icon = {
//                            Icon(
//                                painterResource(id = R.drawable.investmenticon),
//                                contentDescription = "Investment"
//                            )
//                        }
//                    )
//                    NavigationBarItem(
//                        selected = backStackEntry.value?.destination?.route?.startsWith("quiz")
//                            ?: false,
//                        onClick = { navController.navigate("quiz") },
//                        label = { Text("Quiz", style = MaterialTheme.typography.titleMedium) },
//                        icon = {
//                            Icon(
//                                painterResource(id = R.drawable.quizicon),
//                                contentDescription = "Quiz"
//                            )
//                        }
//                    )
//                    NavigationBarItem(
//                        selected = backStackEntry.value?.destination?.route?.startsWith("more")
//                            ?: false,
//                        onClick = { navController.navigate("more") },
//                        label = { Text("More", style = MaterialTheme.typography.titleMedium) },
//                        icon = {
//                            Icon(
//                                painterResource(id = R.drawable.moreicon),
//                                contentDescription = "More"
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Login.route,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(Screen.Login.route) {
//                showBottomBar.value = false
//                LoginScreen(navController)
//            }
//            composable(Screen.Register.route) {
//                showBottomBar.value = false
//                RegisterScreen(navController)
//            }
//            composable(Screen.Main.route) {
//                showBottomBar.value = true
//                MainScreen(navController, expenseViewModel, stockHoldingViewModel, stockSearchViewModel)//, searchViewModel)
//            }
//            composable("transaction") {
//                showBottomBar.value = true
//                Transaction(navController, expenseViewModel)
//            }
//            composable("investment") {
//                showBottomBar.value = true
//                Investment(navController, stockHoldingViewModel, stockSearchViewModel)
//            }
//            composable("quiz") {
//                showBottomBar.value = true
//                Quiz(navController, "Quiz")
//            }
//            composable("more") {
//                showBottomBar.value = true
//                More(navController, expenseViewModel, stockHoldingViewModel)
//            }
//            composable("transaction/add") {
//                showBottomBar.value = true
//                Add(navController, expenseViewModel)
//            }
//            composable("investment/addstockholding") {
//                showBottomBar.value = true
//                AddStockHolding(navController, stockHoldingViewModel)
//            }
//            composable("quiz/CPF") {
//                showBottomBar.value = true
//                QuizGamePage(navController, "CPF")
//            }
//            composable("quiz/TVM") {
//                showBottomBar.value = true
//                QuizGamePage(navController, "TVM")
//            }
//            composable("quiz/stock") {
//                showBottomBar.value = true
//                QuizGamePage(navController, "stock")
//            }
//
//            composable("companyDetails/{symbol") {
//                showBottomBar.value = true
//                CompanyDetailsScreen()
//            }
//        }
//    }
//}

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