package com.example.healthandmind.ui.components.previewparameters

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.healthandmind.domain.entities.Food

class Babbo : PreviewParameterProvider<Food> {
    override val values: Sequence<Food>
        get() = sequenceOf(
            Food(
                calories = 36.37,
                name = "Max Chase",
                protein = 38.39,
                carbs = 40.41,
                fat = 42.43,
                servingWeight = 44.45,
                kCalCoeff = 46.47
            ),
            Food(
                calories = 60.61,
                name = "Betsy McKinney",
                protein = 62.63,
                carbs = 64.65,
                fat = 66.67,
                servingWeight = 68.69,
                kCalCoeff = 70.71
            )
        )
}
