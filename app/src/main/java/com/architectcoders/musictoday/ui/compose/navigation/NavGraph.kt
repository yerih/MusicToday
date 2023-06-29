package com.architectcoders.musictoday.ui.compose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architectcoders.musictoday.ui.compose.detail.DetailScreen
import com.architectcoders.musictoday.ui.compose.home.HomeScreen


@Composable
fun NavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavCommand.Type(Feature.HOME).route
    ){
        composable(NavCommand.Type(Feature.HOME).route){
            HomeScreen{ item ->
                navController.navigate(NavCommand.TypeDetail(Feature.DETAIL).buildRoute(item.id))
            }
        }

        composable(
            route = NavCommand.TypeDetail(Feature.DETAIL).route,
            arguments = NavCommand.TypeDetail(Feature.DETAIL).args
        ){
            DetailScreen{ navController.navigateUp() }
        }
    }
}


