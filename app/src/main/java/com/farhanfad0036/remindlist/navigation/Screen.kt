package com.farhanfad0036.remindlist.navigation

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
}