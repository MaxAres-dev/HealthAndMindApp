package com.example.healthandmind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.customColorGraph
import com.example.compose.customColorGraph2
import com.example.healthandmind.R
import com.example.healthandmind.ui.screen.MacroVal
import com.example.healthandmind.ui.screen.Macros
import javax.crypto.Mac

@Preview (showBackground = true)
@Composable
fun ColumnScope.MacronutrientsCard(
    modifier: Modifier = Modifier,
    macros: Macros = Macros(protein = MacroVal(0.0,0f),carbs = MacroVal( value = 0f, percentage = 0.0), fats = MacroVal( 0.0, 0f) )
) {
    Card(
        modifier = Modifier
            .weight(1.3f)
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = "Macronutrients",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        HorizontalDivider()
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MacronutrientRow(
                text = "Protein ="  + "%.2f".format(macros.protein.value) + "g",
                color = MaterialTheme.colorScheme.primary
            )
            MacronutrientRow(
                text = "Carbs = " + "%.2f".format(macros.carbs.value) + "g",
                color = customColorGraph
                )
            MacronutrientRow(
                text = "Fat = " + "%.2f".format(macros.fats.value) + "g",
                color = customColorGraph2
            )
        }
    }
}

@Composable
fun MacronutrientRow(
    text: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.radio_button_checked_24dp_321d71_fill0_wght400_grad0_opsz24),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp),
            tint = color,
        )

        Spacer(modifier = Modifier
            .width(8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
        )
    }
}