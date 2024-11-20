package com.example.healthandmind.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.customColorGraph
import com.example.compose.customColorGraph2
import com.example.healthandmind.R
import com.example.healthandmind.ui.components.AppBottomBar
import com.example.healthandmind.ui.components.AppTopBar
import com.example.healthandmind.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import kotlin.jvm.internal.RepeatableContainer

object DiaryDestination : NavigationDestination {
    override val route = "diary"
    override val titleRes = "Diary"
}


@Composable
fun Diary(
    viewModel: DiaryViewModel,
    onSearchClicked: () -> Unit = { },
    onPersonClicked: () -> Unit = { },
    onHomeClicked: () -> Unit = { },
    navigateToDiarySelection: () -> Unit,
    navigateToTrainingScreen: () -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            AppBottomBar(
                onSearchClicked = onSearchClicked,
                onHomeClicked = onHomeClicked,
                onPersonClicked = onPersonClicked,
                selectedSearch = true
            )
        },
        topBar = {
            AppTopBar()
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                DiaryContent(
                    type = "Breakfast",
                    updateType = { viewModel.updateUiState(it) },
                    setMealType = { viewModel.onMealSelect() },
                    navigateToDiarySelection = navigateToDiarySelection,
                    icon = R.drawable.bakery_dining_24dp_321d71_fill0_wght400_grad0_opsz24
                )
            }
            item {
                DiaryContent(
                    type = "Lunch",
                    updateType = { viewModel.updateUiState(it) },
                    setMealType = { viewModel.onMealSelect() },
                    navigateToDiarySelection = navigateToDiarySelection,
                    icon = R.drawable.dinner_dining_24dp_321d71_fill0_wght400_grad0_opsz24
                )
            }
            item {
                DiaryContent(
                    type = "Snack",
                    updateType = { viewModel.updateUiState(it) },
                    setMealType = { viewModel.onMealSelect() },
                    navigateToDiarySelection = navigateToDiarySelection,
                    icon = R.drawable.grocery_24dp_321d71_fill0_wght400_grad0_opsz24
                )
            }
            item {
                DiaryContent(
                    type = "Dinner",
                    updateType = { viewModel.updateUiState(it) },
                    setMealType = { viewModel.onMealSelect() },
                    navigateToDiarySelection = navigateToDiarySelection,
                    icon = R.drawable.tapas_24dp_321d71_fill0_wght400_grad0_opsz24
                )
            }
        }
    }
}



@Preview
@Composable
fun DiaryContent(
    updateType: (String) -> Unit = {},
    setMealType: () -> Unit = {},
    navigateToDiarySelection: () -> Unit = {},
    type: String = "",
    icon : Int = R.drawable.fork_spoon_24dp_e8eaed_fill0_wght400_grad0_opsz24
) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(12.dp, RectangleShape)
            .clickable {
                updateType(type)
                setMealType()
                navigateToDiarySelection()
            },
        shape = RoundedCornerShape(8.dp),

        ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = type,
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Start,
        )
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "push here to wiew \nmore details about $type \nand to insert new foods",
                style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Light),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(8.dp)
            )

            Icon (
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = customColorGraph2,
                modifier = Modifier
                    .size(40.dp)

            )
        }
    }
}
