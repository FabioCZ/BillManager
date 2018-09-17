package com.gottlicher.billmanager.di

import com.gottlicher.billmanager.BillManagerApplication
import com.gottlicher.billmanager.views.AddBillActivity
import com.gottlicher.billmanager.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: BillManagerApplication)

    fun inject(mainActivity: MainActivity)
    fun inject(addBillActivity: AddBillActivity)
}