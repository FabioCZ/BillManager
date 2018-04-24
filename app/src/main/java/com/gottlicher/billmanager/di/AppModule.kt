package com.gottlicher.billmanager.di

import android.app.Application
import android.content.Context
import com.gottlicher.billmanager.model.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    /**
     * Allow the application context to be injected but require that it be annotated with [ ][ForApplication] to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideDb() : BoxStore = MyObjectBox.builder().androidContext(application).build()
}