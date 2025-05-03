package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanfad0036.remindlist.database.PekerjaanDao
import com.farhanfad0036.remindlist.model.Pekerjaan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class MainViewModel(dao: PekerjaanDao) : ViewModel() {

    val data: StateFlow<List<Pekerjaan>> = dao.getPekerjaan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getPekerjaan(id: Long): Pekerjaan? {
        return data.value.find { it.id == id }
    }
}

