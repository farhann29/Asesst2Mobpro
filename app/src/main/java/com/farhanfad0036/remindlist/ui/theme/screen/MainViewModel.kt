package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.farhanfad0036.remindlist.database.PekerjaanDao
import com.farhanfad0036.remindlist.model.Pekerjaan
import com.farhanfad0036.remindlist.util.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


enum class FilterType {
    SEMUA,
    SELESAI,
    BELUM_SELESAI,
    DEADLINE_TERDEKAT,
    TERLAMBAT,
}


class MainViewModel(private val dao: PekerjaanDao, private val dataStore: SettingsDataStore) : ViewModel() {

    private val _filter = MutableStateFlow(FilterType.SEMUA)
    val filter: StateFlow<FilterType> = _filter

    private val allData: Flow<List<Pekerjaan>> = dao.getPekerjaan()
    val deletedData: StateFlow<List<Pekerjaan>> = dao.getDeletedPekerjaan()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            dataStore.filterFlow.collect { savedFilter ->
                try {
                    _filter.value = savedFilter
                } catch (e: IllegalArgumentException) {
                    _filter.value = FilterType.SEMUA
                    dataStore.saveFilter(FilterType.SEMUA)
                }

            }
        }
    }

    fun setFilter(newFilter: FilterType) {
        viewModelScope.launch {
            dataStore.saveFilter(newFilter)
            _filter.value = newFilter
        }
    }


    val filteredData: StateFlow<List<Pekerjaan>> = _filter
        .flatMapLatest { currentFilter ->
        when (currentFilter) {
            FilterType.SEMUA -> allData.map { it.filter { !it.isDeleted } }
            FilterType.SELESAI -> allData.map { list -> list.filter { it.selesai == "Selesai" && !it.isDeleted} }
            FilterType.BELUM_SELESAI -> allData.map { list -> list.filter { it.selesai == "Belum selesai" && !it.isDeleted } }
            FilterType.DEADLINE_TERDEKAT -> allData.map { list ->
                list.filter { it.selesai == "Belum selesai" && it.deadline > System.currentTimeMillis() &&
                        !it.isDeleted}
                    .sortedBy { it.deadline }
            }
            FilterType.TERLAMBAT -> allData.map { list ->
                list.filter { it.selesai == "Belum selesai" && it.deadline < System.currentTimeMillis() &&
                        !it.isDeleted }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
    fun softDelete(id: Long) {
        viewModelScope.launch { dao.softDelete(id) }
    }
    fun restore(id: Long) {
        viewModelScope.launch { dao.restore(id) }
    }
    fun clearRecycleBin() {
        viewModelScope.launch { dao.deleteAllDeleted() }
    }
    fun permanentDelete(id: Long) {
        viewModelScope.launch {
            dao.deleteById(id)
        }
    }
}



fun FilterType.toDisplayString(): String {
    return when (this) {
        FilterType.SEMUA -> "Semua"
        FilterType.BELUM_SELESAI -> "Belum selesai"
        FilterType.DEADLINE_TERDEKAT -> "Deadline terdekat (Belum selesai)"
        FilterType.TERLAMBAT -> "Terlambat"
        FilterType.SELESAI -> "Selesai"
    }
}
