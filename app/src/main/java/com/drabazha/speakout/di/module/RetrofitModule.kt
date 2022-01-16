package com.drabazha.speakout.di.module

import com.drabazha.speakout.service.SentenceGeneratorApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule(private var baseUrl: String) {

    @Provides
    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }



    @Provides
    fun sentenceGeneratorApi(retrofit: Retrofit): SentenceGeneratorApi =
        retrofit.create(SentenceGeneratorApi::class.java)
}