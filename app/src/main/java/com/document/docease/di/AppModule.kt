package com.document.docease.di

import android.content.Context
import android.content.SharedPreferences
import com.document.docease.utils.SHARED_PREFERENCES_FILE_NAME
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
    fun providesStorageUtil(@ApplicationContext context: Context, sharedPreferences: SharedPreferences): StorageUtils {
        return StorageUtils(context)
    }



    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE)

    }


}