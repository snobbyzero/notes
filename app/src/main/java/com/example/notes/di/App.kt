package com.example.notes.di

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.example.notes.di.component.DaggerApplicationComponent
import javax.inject.Inject

class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}