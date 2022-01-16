package com.drabazha.speakout.di.module

import android.content.Context
import android.view.GestureDetector
import androidx.core.view.GestureDetectorCompat
import com.drabazha.speakout.database.AppDatabase
import com.drabazha.speakout.listener.SwipeGestureListener
import com.drabazha.speakout.model.SentenceHolder
import com.drabazha.speakout.service.ConfigService
import com.drabazha.speakout.service.ConfigServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun provideConfigService(db: AppDatabase): ConfigService {
        return ConfigServiceImpl(db)
    }

    @Provides
    fun provideOnGestureListener(): GestureDetector.OnGestureListener {
        return SwipeGestureListener()
    }

    @Provides
    fun provideGestureDetectorCompat(context: Context): GestureDetectorCompat {
        return GestureDetectorCompat(context, provideOnGestureListener())
    }
}