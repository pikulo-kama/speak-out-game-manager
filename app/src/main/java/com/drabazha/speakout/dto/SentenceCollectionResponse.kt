package com.drabazha.speakout.dto

import com.google.gson.annotations.SerializedName

class SentenceCollectionResponse {

    @SerializedName("sentences")
    var sentences = ArrayList<String>()
}