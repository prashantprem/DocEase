package com.document.docease.di

import android.content.Context
import com.document.docease.utils.StorageUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesStorageUtil(@ApplicationContext context: Context): StorageUtils {
        return StorageUtils(context)
    }


}