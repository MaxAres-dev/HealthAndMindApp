package com.example.healthandmind.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightGraph(
    currentWeek: List<String>,
    weaklyWeights: List<Double>
) {
    val parsedCurrentWeek = currentWeek.map { it.split("-")[2] + "/" + it.split("-")[1] }

    Card(
        modifier = Modifier
            .height(300.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "Your Weight Progress",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider()
        ComposableLineChart(
            values = weaklyWeights,
            labels = parsedCurrentWeek,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}