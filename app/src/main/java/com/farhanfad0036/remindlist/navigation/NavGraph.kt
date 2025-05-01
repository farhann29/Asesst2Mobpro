package com.farhanfad0036.remindlist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farhanfad0036.remindlist.ui.theme.screen.DetailScreen
import com.farhanfad0036.remindlist.ui.theme.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination =Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen()
        }
    }
}