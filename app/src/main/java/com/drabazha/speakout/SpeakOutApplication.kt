package com.drabazha.speakout

import android.app.Application
import com.drabazha.speakout.di.ApplicationComponent
import com.drabazha.speakout.di.DaggerApplicationComponent
import com.drabazha.speakout.di.module.AppModule
import com.drabazha.speakout.di.module.RetrofitModule
import com.drabazha.speakout.di.module.ServiceModule
import com.drabazha.speakout.util.SentenceApiConstants.SENTENCE_BUILDER_API_BASE_URL

class SpeakOutApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule(SENTENCE_BUILDER_API_BASE_URL))
            .serviceModule(ServiceModule())
            .build()
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }
}