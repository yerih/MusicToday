package com.architectcoders.musictoday.ui.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.architectcoders.musictoday.ui.compose.navigatePopUpToStartDest
import com.architectcoders.musictoday.ui.compose.navigation.NavGraph
import com.architectcoders.musictoday.ui.compose.ui.theme.MusicTodayTheme
import com.architectcoders.musictoday.ui.compose.ui_components.BottomNavBar

@Composable
fun MusicTodayApp(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    ScreenApp {
        Scaffold(
            bottomBar = {
                BottomNavBar(currentRoute = currentRoute){ item ->
                    navController.navigatePopUpToStartDest(item.navCommand.route)
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)){
                NavGraph(navController)
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


