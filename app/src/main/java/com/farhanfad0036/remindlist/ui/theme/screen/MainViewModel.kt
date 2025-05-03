package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import com.farhanfad0036.remindlist.model.Pekerjaan


class MainViewModel : ViewModel() {

    val data = listOf<Pekerjaan>()

    fun getPekerjaan(id: Long): Pekerjaan? {
        return data.find { it.id == id }
    }
}

