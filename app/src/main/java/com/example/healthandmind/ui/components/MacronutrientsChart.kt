package com.example.healthandmind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.customColorGraph
import com.example.compose.customColorGraph2
import com.example.healthandmind.ui.screen.Macros

@Composable
fun RowScope.MacronutrientsChart(macros: Macros) {
    Card(modifier = Modifier
        .weight(0.8f)
        .height(230.dp)
        .padding(8.dp)
    ) {
        Text(
            text = "Macronutrients %",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        )
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ComposablePieChart(
                pieDatas = listOf(
                    PieData(
                        label = "Protein",
                        percentage = macros.protein.percentage,
                    ),
                    PieData(
                        label = "Carbs",
                        percentage = macros.carbs.percentage,
                    ),
                    PieData(
                        label = "Fat",
                        percentage = macros.fats.percentage,
                    )
                ),
                firstColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    customColorGraph,
                    customColorGraph2
                ),
                secondaryColors = listOf(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.tertiaryContainer

                ),
            )

        }
    }
}