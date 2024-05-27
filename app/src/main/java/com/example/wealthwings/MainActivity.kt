package com.example.wealthwings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wealthwings.pages.Add
import com.example.wealthwings.pages.FAQ
import com.example.wealthwings.pages.Investment
import com.example.wealthwings.pages.More
import com.example.wealthwings.pages.Profile
import com.example.wealthwings.pages.Quiz
import com.example.wealthwings.pages.Transaction
import com.example.wealthwings.ui.theme.WealthWingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WealthWingsTheme {
                val navController =
                    rememberNavController() //this is to create an instance to manage app navigation within NavHost
                val backStackEntry =
                    navController.currentBackStackEntryAsState() //keep track of current route and its state,
                // to update the UI based on its state

                Scaffold(
                    bottomBar = {
                        NavigationBar(containerColor = com.example.wealthwings.ui.theme.BottomBar) {
                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route?.startsWith(
                                    "transaction"
                                )
                                    ?: false, //app will crash if not selected (to track the state)
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
                                selected = backStackEntry.value?.destination?.route == "investment",
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
                                selected = backStackEntry.value?.destination?.route == "quiz",
                                onClick = { navController.navigate("quiz") },
                                label = {
                                    Text("Quiz", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.quizicon),
                                        contentDescription = "Quiz"
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = backStackEntry.value?.destination?.route?.startsWith(
                                    "more"
                                )
                                    ?: false, //if route selected starts with more, more will still be selected, else not selected
                                onClick = { navController.navigate("more") },
                                label = {
                                    Text("More", style = MaterialTheme.typography.titleMedium)
                                },

                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.moreicon),
                                        contentDescription = "More"
                                    )
                                }
                            )

                        }
                    },

                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "transaction"
                        ) {
                            composable("transaction") {
                                Surface(
                                    //only for design puposes
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Transaction(navController, "Transaction")
                                }
                            }
                            composable("investment") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Investment(navController,"Investment")
                                }
                            }
                            composable("quiz") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Quiz(navController, "Quiz")
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

                            composable("more/myprofile") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Profile(navController, "Profile")
                                }
                            }

                            composable("more/FAQ") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    FAQ(navController, "FAQ")
                                }
                            }

                            composable("transaction/add") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Add(navController)
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