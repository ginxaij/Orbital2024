package com.example.wealthwings.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.wealthwings.R
import com.example.wealthwings.ui.theme.SystemGray04

@Composable
fun LargeButton(navController: NavController, route : String) {
    LargeFloatingActionButton(
        onClick = { navController.navigate(route) },
        shape = CircleShape,
        containerColor = SystemGray04
    ) {
        Icon(
            painterResource(id = R.drawable.addicon),
            contentDescription = "Transaction")
    }
}