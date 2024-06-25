package com.maha.topmusicalbums.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maha.topmusicalbums.ui.view.albumdetailscreen.AlbumDetailScreen
import com.maha.topmusicalbums.ui.view.homescreen.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AlbumDetail : Screen("albumDetail/{albumId}") {
        fun createRoute(albumId: Int) = "albumDetail/$albumId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = Screen.Home.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.AlbumDetail.route,
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId")
            AlbumDetailScreen(albumId = albumId, navController = navController)
        }
    }
}