package com.farhanfad0036.remindlist.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farhanfad0036.remindlist.database.PekerjaanDb
import com.farhanfad0036.remindlist.ui.theme.screen.DetailViewModel
import com.farhanfad0036.remindlist.ui.theme.screen.MainViewModel

class ViewModelFactory (
    private val context: Context,
    private val dataStore: SettingsDataStore? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = PekerjaanDb.getInstance(context).dao
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                if (dataStore == null) throw IllegalArgumentException("DataStore is required for MainViewModel")
                MainViewModel(dao, dataStore) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(dao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

