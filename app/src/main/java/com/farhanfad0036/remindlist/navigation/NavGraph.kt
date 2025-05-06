package com.farhanfad0036.remindlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farhanfad0036.remindlist.ui.theme.screen.DetailScreen
import com.farhanfad0036.remindlist.ui.theme.screen.KEY_ID_PEKERJAAN
import com.farhanfad0036.remindlist.ui.theme.screen.MainScreen
import com.farhanfad0036.remindlist.ui.theme.screen.MainViewModel
import com.farhanfad0036.remindlist.ui.theme.screen.RecycleBinScreen
import com.farhanfad0036.remindlist.util.SettingsDataStore
import com.farhanfad0036.remindlist.util.ViewModelFactory

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val factory = ViewModelFactory(context, dataStore)
    val mainViewModel: MainViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination =Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(viewModel = mainViewModel, navController = navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_PEKERJAAN) {type = NavType.LongType}
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_PEKERJAAN)
            DetailScreen(navController, id)
        }
    }
}