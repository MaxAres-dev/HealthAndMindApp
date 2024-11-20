package com.example.healthandmind.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination

object FoodDetail : NavigationDestination {
    override val route = "FoodDetail"
    override val titleRes = "Food Detail"
}

@Composable
fun FoodDetail(
    modifier: Modifier = Modifier,
    onBackNav : () -> Unit,
    viewModel: FoodDetailViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                enableBackNav = true,
                onBackNav = onBackNav,
                title = "Food Detail"
            )
        }
    ) {
        Column(modifier.padding(it)) {
            FoodDetailContent(
                food = uiState.value.food,
                modifier = modifier
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailContent(
    modifier: Modifier = Modifier,
    food: Food = Food(0.0, "", 0.0, 0.0, 0.0, 0.0, 0.0)
) {
    Column(modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "name: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.name,
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "calories per 100 grams: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text =  String.format("%.2f", food.kCalCoeff * 100), // Limita a due decimali
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "protein: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.protein.toInt().toString(),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "fat: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.fat.toInt().toString(),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "carbohydrates: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.carbs.toInt().toString(),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "quantity: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.servingWeight.toString(),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = "total calories: ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (food.kCalCoeff * food.servingWeight).toString(),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
    }
}