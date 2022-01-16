package com.drabazha.speakout.service

import com.drabazha.speakout.dto.SentenceCollectionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SentenceGeneratorApi {

    @GET("/api/v2/sentences/generate")
    suspend fun getSentences(@Query("c") count: Int = 1): Response<SentenceCollectionResponse>
}