package com.drabazha.speakout.model

import com.drabazha.speakout.util.SentenceApiConstants.MIN_SENTENCE_POOL_SIZE

object SentenceHolder {

    private var sentences: ArrayList<String> = ArrayList()
    private var sentencesCursor: Int = -1

    fun next(): SentenceHolder {
        ++sentencesCursor
        return this
    }

    fun get(): String {
        return sentences[sentencesCursor]
    }

    fun addAll(sentences: ArrayList<String>) {
        this.sentences.addAll(sentences)
    }

    fun add(sentence: String) {
        this.sentences.add(sentence)
    }

    fun init(sentences: ArrayList<String>) {
        clear()
        addAll(sentences)
        sentencesCursor = -1
    }

    fun needsRefill(): Boolean {
        return sentences.size - sentencesCursor + 1 <= MIN_SENTENCE_POOL_SIZE
    }

    fun clear() {
        this.sentences = ArrayList()
    }
}