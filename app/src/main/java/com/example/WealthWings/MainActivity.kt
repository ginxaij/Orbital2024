package com.example.WealthWings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.WealthWings.pages.More
import com.example.WealthWings.pages.Transaction
import com.example.WealthWings.ui.theme.GoodMoneyTheme
import com.example.goodmoney.R

// Test commit again
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoodMoneyTheme {
                val navController = rememberNavController() //this is to create an instance to manage app navigation within NavHost
                val backStackEntry = navController.currentBackStackEntryAsState() //keep track of current route and its state,
                // to update the UI based on its state
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "transaction", //app will crash if not selected (to track the state)
                                onClick = { navController.navigate("transaction") },
                                label = {
                                    Text("Transaction", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.transactionicon),
                                        contentDescription = "Transaction")
                                }
                            )

                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "investment",
                                onClick = { navController.navigate("investment") },
                                label = {
                                    Text("Investment", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.investmenticon),
                                        contentDescription = "Investment")
                                }
                            )

                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "quiz",
                                onClick = { navController.navigate("quiz") },
                                label = {
                                    Text("Quiz", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.quizicon),
                                        contentDescription = "Quiz")
                                }
                            )

                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route == "more",
                                onClick = { navController.navigate("more") },
                                label = {
                                    Text("More", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.moreicon),
                                        contentDescription = "More")
                                }
                            )

                            }
                        },

                    content = { innerPadding ->
                        NavHost(navController = navController, startDestination = "transaction") {
                            composable("transaction") {
                                Surface( //only for design puposes
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Transaction(navController,"Transaction")
                                }
                            }
                            composable("investment") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Greeting("Investment")
                                }
                            }
                            composable("quiz") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Greeting("Quiz")
                                }
                            }
                            composable("more") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    More(navController)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "oi $name!",
        modifier = modifier
    )
}

/*@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    GoodMoneyTheme {
        Surface {
            Greeting("Android")
        }
    }
}*/