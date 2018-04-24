package com.gottlicher.billmanager.di

import com.gottlicher.billmanager.BillManagerApplication
import com.gottlicher.billmanager.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: BillManagerApplication)

    fun inject(mainActivity: MainActivity)
}