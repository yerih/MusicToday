package com.architectcoders.musictoday.ui.compose.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architectcoders.musictoday.ui.compose.detail.DetailScreen
import com.architectcoders.musictoday.ui.compose.home.HomeScreen


@Composable
fun NavGraph(navController: NavHostController){

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

        composable(
            route = NavCommand.Type(Feature.EVENTS).route,
        ){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "events", textAlign = TextAlign.Center, fontSize = 34.sp)
            }
        }
        composable(
            route = NavCommand.Type(Feature.SEARCH).route,
        ){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "search", textAlign = TextAlign.Center, fontSize = 34.sp)
            }
        }
    }
}


