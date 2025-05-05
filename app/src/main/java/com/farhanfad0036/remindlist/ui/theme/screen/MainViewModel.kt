package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanfad0036.remindlist.database.PekerjaanDao
import com.farhanfad0036.remindlist.model.Pekerjaan
import com.farhanfad0036.remindlist.util.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


enum class FilterType {
    SEMUA,
    SELESAI,
    BELUM_SELESAI,
    DEADLINE_TERDEKAT
}


class MainViewModel(private val dao: PekerjaanDao, private val dataStore: SettingsDataStore) : ViewModel() {

    private val _filter = MutableStateFlow(FilterType.SEMUA)
    val filter: StateFlow<FilterType> = _filter

    init {
        viewModelScope.launch {
            dataStore.filterFlow.collect { savedFilter ->
                _filter.value = savedFilter
            }
        }
    }

    fun setFilter(newFilter: FilterType) {
        viewModelScope.launch {
            dataStore.saveFilter(newFilter)
            _filter.value = newFilter
        }
    }


    val filteredData: StateFlow<List<Pekerjaan>> = combine(
        dao.getPekerjaan(),
        filter
    ) { allPekerjaan, currentFilter ->
        when (currentFilter) {
            FilterType.SEMUA -> allPekerjaan
            FilterType.SELESAI -> allPekerjaan.filter { it.selesai == "Selesai" }
            FilterType.BELUM_SELESAI -> allPekerjaan.filter { it.selesai == "Belum selesai" }
            FilterType.DEADLINE_TERDEKAT -> allPekerjaan
                .filter { it.selesai == "Belum selesai" }
                .sortedBy { it.deadline }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}

fun FilterType.toDisplayString(): String {
    return when (this) {
        FilterType.SEMUA -> "Semua"
        FilterType.SELESAI -> "Selesai"
        FilterType.BELUM_SELESAI -> "Belum selesai"
        FilterType.DEADLINE_TERDEKAT -> "Deadline terdekat (Belum selesai)"
    }
}
