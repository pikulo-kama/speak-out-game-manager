package com.drabazha.speakout.di

import android.view.GestureDetector
import com.drabazha.speakout.activity.GameActivity
import com.drabazha.speakout.activity.ScoreActivity
import com.drabazha.speakout.activity.SettingsActivity
import com.drabazha.speakout.activity.TeamsActivity
import com.drabazha.speakout.di.module.AppModule
import com.drabazha.speakout.di.module.RetrofitModule
import com.drabazha.speakout.di.module.ServiceModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RetrofitModule::class,
    ServiceModule::class,
    AndroidSupportInjectionModule::class ])
interface ApplicationComponent {

    fun inject(activity: TeamsActivity)

    fun inject(activity: GameActivity)

    fun inject(activity: ScoreActivity)

    fun inject(activity: SettingsActivity)

    fun inject(listener: GestureDetector.OnGestureListener)
}