package com.example.notes.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.notes.util.SharedPrefsManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(application: Application) = application.getSharedPreferences(NAME, MODE)

    @Singleton
    @Provides
    fun provideSharedPrefsManager(sharedPreferences: SharedPreferences) =
        SharedPrefsManager(sharedPreferences)

    companion object {
        const val NAME = "Notes"
        const val MODE = Context.MODE_PRIVATE
    }
}