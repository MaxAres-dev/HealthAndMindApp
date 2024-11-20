package com.example.healthandmind.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(
    showBackground = true,
)
@Composable
fun AppBottomBar(
    onSearchClicked: () -> Unit = {},
    onPersonClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    selectedHome: Boolean = false,
    selectedSearch: Boolean = false,
    selectedPerson: Boolean = false,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
    ) {
        NavigationBarItem(
            onClick = {
                onHomeClicked()
            },
            selected = selectedHome,
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
        )
        VerticalDivider(
            modifier = modifier.height(50.dp),
            thickness = 2.dp
        )
        NavigationBarItem(
            onClick = onSearchClicked,
            selected = selectedSearch,
            icon = {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        VerticalDivider(
            modifier = modifier.height(50.dp),
            thickness = 2.dp
        )
        NavigationBarItem(
            onClick = onPersonClicked,
            selected = selectedPerson,
            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
            }
        )
    }
}