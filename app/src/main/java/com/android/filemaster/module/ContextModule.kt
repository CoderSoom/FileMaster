package com.android.filemaster.module

import android.content.Context
import com.android.filemaster.base.BaseApplication
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class ContextModule {
    @Singleton
    @Provides
    @Named("AppContext")
    fun provideContext(application: BaseApplication): Context {
        return application.applicationContext
    }
}