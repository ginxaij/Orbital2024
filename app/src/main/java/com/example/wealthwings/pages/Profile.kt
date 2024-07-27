package com.example.wealthwings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wealthwings.components.LargeButton
import com.example.wealthwings.components.TableRow
import com.example.wealthwings.ui.theme.Background
import com.example.wealthwings.ui.theme.BackgroundElevated
import com.example.wealthwings.ui.theme.Divider
import com.example.wealthwings.ui.theme.Shapes
import com.google.firebase.auth.FirebaseAuth

//import com.example.wealthwings.ui.theme.TopAppBarBackGround


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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
                    TableRow("Change Email", hasArrow = true,
                        modifier = Modifier.clickable { navController.navigate("more/myprofile/changeemail") }) //categories will be inside, can view the profile of the person
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow("Change Password", hasArrow = true,
                        modifier = Modifier.clickable { navController.navigate("more/myprofile/changepassword") })
                    Divider(thickness = 1.dp, color = Divider)
                    TableRow("Delete Account", hasArrow = true,
                        modifier = Modifier.clickable { navController.navigate("more/myprofile/deleteuser") },
                        isDestructive = true)

                }
            }
        })
}