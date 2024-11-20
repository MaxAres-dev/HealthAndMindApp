package com.example.healthandmind.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun TrainingTimeItem(
    modifier: Modifier = Modifier,
    onUpClicked: () -> Unit = {},
    onDownClicked: () -> Unit = {},
    enabledButtonCheck: () -> Unit = {},
    time: Int = 0,

    ) {
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Training time",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier
                )
                Icon(
                    Icons.Outlined.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onUpClicked()
                        enabledButtonCheck()
                    }
                )
                Icon(
                    Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        // TODO : implementare la logica per diminuire il tempo
                        onDownClicked()
                        enabledButtonCheck()
                    }
                )
                Text(
                    text = time.toString(),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier
                )
            }
        }
    }