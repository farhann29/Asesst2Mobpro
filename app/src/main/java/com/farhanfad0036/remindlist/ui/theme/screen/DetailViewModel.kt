package com.farhanfad0036.remindlist.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanfad0036.remindlist.database.PekerjaanDao
import com.farhanfad0036.remindlist.model.Pekerjaan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class DetailViewModel(private val dao: PekerjaanDao) : ViewModel() {

    fun insert(judul: String, deskripsi: String, deadline: Long, selesai: String) {
        val pekerjaan = Pekerjaan(
            judul = judul,
            deskripsi = deskripsi,
            deadline = deadline,
            selesai = selesai
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(pekerjaan)
        }
    }
    fun getPekerjaan(id: Long): Pekerjaan? {
        return null
    }
}