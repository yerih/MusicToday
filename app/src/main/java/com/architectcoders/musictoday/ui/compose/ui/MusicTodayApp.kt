package com.architectcoders.musictoday.ui.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.architectcoders.musictoday.ui.compose.navigation.NavGraph
import com.architectcoders.musictoday.ui.compose.navigation.NavItem
import com.architectcoders.musictoday.ui.compose.ui.theme.MusicTodayTheme

@Composable
fun MusicTodayApp(){
    ScreenApp {
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    NavItem.values().forEach {item ->
                        val title = stringResource(id = item.title)
                        BottomNavigationItem(
                            selected = false,
                            onClick = {  },
                            icon = { Icon(imageVector = item.icon, contentDescription = title) },
                            label = { Text(text = title)}
                        )
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)){
                NavGraph()
            }
        }
    }
}

@Composable
fun ScreenApp(content: @Composable ()->Unit){
    MusicTodayTheme {
        content()
    }
}


