package com.example.wealthwings.components

import android.telecom.Call.Details
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wealthwings.R
import com.example.wealthwings.ui.theme.Destructive
import com.example.wealthwings.ui.theme.TextPrimary
import com.example.wealthwings.ui.theme.Typography


@Composable
fun TableRow(
    label: String,
    modifier: Modifier = Modifier,
    detail: (@Composable RowScope.() -> Unit)? = null,
    hasArrow: Boolean = false,
    isDestructive: Boolean = false
) {

    Row(modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        val textColor = if (isDestructive) Destructive else TextPrimary
        Text(text = label, style = Typography.bodyMedium, color = textColor)
        if (hasArrow) {
            Icon(
                painterResource(id = R.drawable.arrowicon),
                contentDescription = "Arrow"
            )
        }

        if (detail != null) {
            detail()
        }
    }
}


