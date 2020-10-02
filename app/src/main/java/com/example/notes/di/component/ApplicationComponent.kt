package com.example.notes.di.component

import android.app.Application
import com.example.notes.di.App
import com.example.notes.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    NetworkModule::class,
    SharedPrefsModule::class,
    ViewModelModule::class
])
interface ApplicationComponent : AndroidInjector<App> {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }


}