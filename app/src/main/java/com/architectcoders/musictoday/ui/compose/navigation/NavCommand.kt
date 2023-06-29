package com.architectcoders.musictoday.ui.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.architectcoders.musictoday.R


enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int
){
    HOME(NavCommand.Type(Feature.HOME), Icons.Default.Home, R.string.home),
    EVENTS(NavCommand.Type(Feature.EVENTS), Icons.Default.Event, R.string.events),
    SEARCH(NavCommand.Type(Feature.SEARCH), Icons.Default.Search, R.string.search)
}

sealed class NavCommand(
    internal val feature: Feature,
    private val navArgs: List<NavArgs> = emptyList()
){
    val route = run {
        val toJoin = navArgs.map { "{${it.key}}" }
        listOf(feature.route).plus(toJoin).joinToString("/")
    }
    val args = navArgs.map { navArgument(it.key){type = it.navType} }

    class Type(feature: Feature) : NavCommand(feature)
    class TypeDetail(feature: Feature = Feature.HOME) : NavCommand(feature = feature, navArgs = listOf(NavArgs.Id)){
        fun buildRoute(id: Int) = "${feature.route}/$id"
    }
}


enum class NavArgs(val key: String, val navType: NavType<*>){
    Id("id", navType = NavType.IntType),
}

