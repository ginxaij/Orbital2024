package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Shapes
import com.example.wealthwings.ui.theme.Divider
//import com.example.wealthwings.ui.theme.TopAppBarBackGround

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun More(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("More") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Background
                )
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier.padding(16.dp)
                        .clip(Shapes.medium)
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    TableRow("My Profile", hasArrow = true, modifier = Modifier.clickable { navController.navigate("more/myprofile") }) //categories will be inside, can view the profile of the person
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow("FAQ", hasArrow = true, modifier = Modifier.clickable { navController.navigate("more/FAQ") })
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow(
                        "Erase all data",
                        isDestructive = true)
                }
            }
        }
    )
}