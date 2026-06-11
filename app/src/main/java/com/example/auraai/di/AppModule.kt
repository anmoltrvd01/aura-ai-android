package com.example.auraai.di

import android.content.Context
import com.example.auraai.data.store.UserPreferencesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferencesStore(@ApplicationContext context: Context): UserPreferencesStore {
        return UserPreferencesStore(context)
    }
}
