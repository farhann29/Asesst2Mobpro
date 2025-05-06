package com.farhanfad0036.remindlist.navigation

import com.farhanfad0036.remindlist.ui.theme.screen.KEY_ID_PEKERJAAN

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_PEKERJAAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object RecycleBin : Screen("recycleBin")
}