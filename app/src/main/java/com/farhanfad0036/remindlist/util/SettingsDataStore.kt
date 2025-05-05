package com.farhanfad0036.remindlist.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farhanfad0036.remindlist.ui.theme.screen.FilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {
    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val FILTER_KEY = stringPreferencesKey("selected_filter")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }
    val filterFlow: Flow<FilterType> = context.dataStore.data.map { preferences ->
        val filterName = preferences[FILTER_KEY] ?: FilterType.SEMUA.name
        FilterType.valueOf(filterName)
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }
    suspend fun saveFilter(filter: FilterType) {
        context.dataStore.edit { preferences ->
            preferences[FILTER_KEY] = filter.name
        }
    }
}