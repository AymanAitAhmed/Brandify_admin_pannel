package com.example.pfeadminpannel.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pfeadminpannel.views.allUsers.ListSortingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class ListSortingPreferences constructor(
    private val context: Context
) {
    //create the preference datastore
    companion object {
        val Context.datastore: DataStore<Preferences> by preferencesDataStore("Sorting")
        val CURRENT_SORTING_TYPE = stringPreferencesKey("SortingType")
    }

    //get the current points
    val getSortingType: Flow<String> = context.datastore.data.map { preferences ->
        preferences[CURRENT_SORTING_TYPE] ?: ""
    }

    // to save current points
    suspend fun setSortingType(sortingType: String) {
        context.datastore.edit { preferences ->
            preferences[CURRENT_SORTING_TYPE] = sortingType
        }
    }
}