package com.example.healthandmind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthandmind.R

@Composable
fun ProgressBar(
    progressBarPercentage: Float,
    foodCalories: Double,
    trainingCalories: Double,
    goalCalories : Double
) {
    val totalCal = foodCalories - trainingCalories
    val progressPerc = if (progressBarPercentage < 0) 0f else progressBarPercentage
    Card(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Daily Calories",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressBar(
                transitionData = CircularTransitionData(progress = progressPerc),
                radius = 180f,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                label = "Calories: ",
                calories = if  (totalCal > 7000 || totalCal < -7000) 9999 else totalCal.toInt()
            )

            Column(
                Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text (
                        text = "Goal Calories: \n ${goalCalories.toInt()} kcal",
                        maxLines = 2,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )

                }
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.fork_spoon_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(26.dp)
                            .weight(1f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "FoodCal: ${foodCalories.toInt()} kcal",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        maxLines = 2,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.local_fire_department_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(26.dp)
                            .weight(1f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Training: ${trainingCalories.toInt()} kcal",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        maxLines = 2,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                }


            }
        }
    }
}