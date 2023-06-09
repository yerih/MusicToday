package com.architectcoders.musictoday.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architectcoders.musictoday.domain.Artist
import com.architectcoders.musictoday.ui.compose.detail.DetailScreen
import com.architectcoders.musictoday.ui.compose.fromJson
import com.architectcoders.musictoday.ui.compose.home.HomeScreen
import com.architectcoders.musictoday.ui.compose.toJson


@Composable
fun NavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavItem.Home.route){
        composable(NavItem.Home.route){
            HomeScreen{ item ->
                navController.navigate(NavItem.Detail.buildRoute(item.id))
            }
        }

        composable(
            route = NavItem.Detail.route,
            arguments = NavItem.Detail.args
        ){
            DetailScreen{ navController.navigateUp() }
        }
    }
}


