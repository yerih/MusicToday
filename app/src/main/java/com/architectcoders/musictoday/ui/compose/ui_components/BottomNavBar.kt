package com.architectcoders.musictoday.ui.compose.ui_components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.architectcoders.musictoday.ui.compose.navigation.NavItem

@Composable
fun BottomNavBar(currentRoute: String, onNavItemCLicked: (NavItem)->Unit){
    BottomNavigation {
        NavItem.values().forEach { item ->
            val title = stringResource(id = item.title)
            BottomNavigationItem(
                selected = currentRoute.contains(item.navCommand.feature.route),
                onClick = { onNavItemCLicked(item) },
                icon = { Icon(imageVector = item.icon, contentDescription = title) },
                label = { Text(text = title)}
            )
        }
    }
}


