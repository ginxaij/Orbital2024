package com.example.wealthwings.pages

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wealthwings.ui.theme.Purple200
import com.example.wealthwings.ui.theme.Purple500
import com.example.wealthwings.ui.theme.Purple700
import com.example.wealthwings.ui.theme.Teal200
import com.example.wealthwings.ui.theme.Blue
import java.math.RoundingMode

@Composable
fun PieChart (
    data: Map<String, Double>,
    radiusOuter: Dp = 60.dp, // 40
    chartBarWidth: Dp = 10.dp, // 5
    animDuration: Int = 1000,
) {
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    val isSingleSlice = data.size == 1

    if (isSingleSlice) {
        // If there is only one slice, it should take up the entire pie chart
        floatValue.add(360f)
    } else {
        // Calculate angles for multiple slices
        data.values.forEach { value ->
            floatValue.add(360 * value.toFloat() / totalSum.toFloat())
        }
    }

    val colors = listOf(
        Purple200,
        Purple500,
        Teal200,
        Purple700,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue =  if (animationPlayed) radiusOuter.value *2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .size(radiusOuter * 2f)
                .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )

                    lastValue += value
                }
            }
        }

        DetailsPieChart(
            data = data,
            colors = colors
        )

    }

}

@Composable
fun DetailsPieChart(
    data: Map<String, Double>,
    colors: List<Color>
) {
    Column(modifier = Modifier
        .padding(top = 0.dp)
        .fillMaxWidth()
    ) {
        data.values.forEachIndexed {index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index]
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Double>,
    height: Dp = 10.dp,
    color: Color
) {
    Surface(
        modifier = Modifier.padding(
            vertical = 2.dp, horizontal = 30.dp),
        color = Color.Transparent
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            )
                .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "$" + data.second.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}