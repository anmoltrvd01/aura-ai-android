package com.example.auraai.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.auraai.data.model.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferencesStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore("user_profile")

    companion object {
        val KEY_NAME    = stringPreferencesKey("name")
        val KEY_AGE     = stringPreferencesKey("age")
        val KEY_PHONE   = stringPreferencesKey("phone")
        val KEY_TRAITS  = stringSetPreferencesKey("traits")
    }

    val profile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            name   = prefs[KEY_NAME] ?: "",
            age    = prefs[KEY_AGE] ?: "",
            phone  = prefs[KEY_PHONE] ?: "",
            traits = (prefs[KEY_TRAITS] ?: emptySet()).toList()
        )
    }

    suspend fun save(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NAME]   = profile.name
            prefs[KEY_AGE]    = profile.age
            prefs[KEY_PHONE]  = profile.phone
            prefs[KEY_TRAITS] = profile.traits.toSet()
        }
    }
}