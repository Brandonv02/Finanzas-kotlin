package com.example.appfinanzas.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {
    private val NAME_KEY = stringPreferencesKey("user_name")
    private val SALARY_KEY = doublePreferencesKey("user_salary")

    suspend fun saveUser(context: Context, name: String, salary: Double) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[SALARY_KEY] = salary
        }
    }

    suspend fun loadUser(context: Context): Pair<String?, Double?> {
        val prefs = context.dataStore.data.first()
        val name = prefs[NAME_KEY]
        val salary = prefs[SALARY_KEY]
        return Pair(name, salary)
    }
}
