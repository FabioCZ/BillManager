package com.gottlicher.billmanager

import android.app.Application
import com.gottlicher.billmanager.di.AppComponent
import com.gottlicher.billmanager.di.AppModule
import com.gottlicher.billmanager.di.DaggerAppComponent

class BillManagerApplication : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }
}