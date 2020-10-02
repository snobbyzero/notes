package com.example.notes.di.module

import com.example.notes.activity.main.MainActivity
import com.example.notes.di.annotation.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivityAndroidInjector(): MainActivity
}