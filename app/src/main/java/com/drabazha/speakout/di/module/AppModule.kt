package com.drabazha.speakout.di.module

import android.content.Context
import android.view.LayoutInflater
import androidx.room.RoomDatabase
import com.drabazha.speakout.SpeakOutApplication
import com.drabazha.speakout.activity.GameActivity
import com.drabazha.speakout.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var app: SpeakOutApplication) {

    @Singleton
    @Provides
    fun application(): SpeakOutApplication = app

    @Singleton
    @Provides
    fun applicationContext(): Context = app.applicationContext

    @Singleton
    @Provides
    fun roomDatabase(applicationContext: Context): AppDatabase =
        AppDatabase.getInstance(applicationContext)
}