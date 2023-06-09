package com.architectcoders.musictoday.ui.compose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.architectcoders.musictoday.data.database.ArtistEntity
import com.architectcoders.musictoday.domain.Artist


sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArgs> = emptyList()
){
    val route = run {
        val toJoin = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(toJoin).joinToString("/")
    }
    val args = navArgs.map { navArgument(it.key){type = it.navType} }

    object Home:    NavItem("home")
    object Detail:  NavItem("detail", listOf(NavArgs.Id)){
        fun buildRoute(id: Int) = "$baseRoute/$id"
    }
}


enum class NavArgs(val key: String, val navType: NavType<*>){
    Id("id", navType = NavType.IntType),
}

